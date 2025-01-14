import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditPostComponent } from './edit-post.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PostService } from '../../../shared/services/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { Category, Post } from '../../../shared/models/post.model';

describe('EditPostComponent', () => {
  let component: EditPostComponent;
  let fixture: ComponentFixture<EditPostComponent>;
  let mockPostService: jasmine.SpyObj<PostService>;
  let mockRouter: jasmine.SpyObj<Router>;
  const mockPost = new Post(1, 1, 'Test Post', 'Content', 'Author', 'DRAFT', Category.TECHNOLOGY, new Date());

  beforeEach(async () => {
    mockPostService = jasmine.createSpyObj('PostService', ['getPostById', 'updatePost']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, EditPostComponent],
      providers: [
        { provide: PostService, useValue: mockPostService },
        { provide: Router, useValue: mockRouter },
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: { get: () => '1' } } }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EditPostComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load post on init', async () => {
    mockPostService.getPostById.and.returnValue(of(mockPost));
    await component.ngOnInit();
    expect(mockPostService.getPostById).toHaveBeenCalledWith(1);
    expect(component.postForm.get('title')?.value).toBe(mockPost.title);
  });

  it('should handle error on init', async () => {
    mockPostService.getPostById.and.returnValue(throwError(() => new Error()));
    await component.ngOnInit();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/draft']);
  });

  it('should update post', async () => {
    mockPostService.updatePost.and.returnValue(of(mockPost));
    component.postForm.patchValue(mockPost);

    await component.onUpdate();

    expect(mockPostService.updatePost).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/draft']);
  });

  it('should submit post for review', async () => {
    mockPostService.updatePost.and.returnValue(of({ ...mockPost, status: 'PENDING' }));
    component.postForm.patchValue(mockPost);

    await component.onSubmitForReview();

    expect(mockPostService.updatePost).toHaveBeenCalled();
    const updateCall = mockPostService.updatePost.calls.first().args[0];
    expect(updateCall.status).toBe('PENDING');
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/draft']);
  });

  it('should not update invalid form', async () => {
    component.postForm.controls['title'].setErrors({ required: true });
    await component.onUpdate();
    expect(mockPostService.updatePost).not.toHaveBeenCalled();
  });
});

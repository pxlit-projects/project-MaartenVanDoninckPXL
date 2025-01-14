import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddPostComponent } from './add-post.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PostService } from '../../../shared/services/post.service';
import { AuthService } from '../../../shared/services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { Category, Post } from '../../../shared/models/post.model';

describe('AddPostComponent', () => {
  let component: AddPostComponent;
  let fixture: ComponentFixture<AddPostComponent>;
  let mockPostService: jasmine.SpyObj<PostService>;
  let mockAuthService: jasmine.SpyObj<AuthService>;
  let mockRouter: jasmine.SpyObj<Router>;
  const mockUser = { userName: 'TestUser', role: 'redacteur' };
  const mockNewPost = new Post(1, 1, 'Test Title', 'Test Content', 'TestUser', 'DRAFT', Category.TECHNOLOGY, new Date());

  beforeEach(async () => {
    mockPostService = jasmine.createSpyObj('PostService', ['addPost']);
    mockAuthService = jasmine.createSpyObj('AuthService', ['getUser']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
    mockAuthService.getUser.and.returnValue(mockUser);

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, AddPostComponent],
      providers: [
        { provide: PostService, useValue: mockPostService },
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AddPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with default values', () => {
    expect(component.postForm.get('title')?.value).toBe('');
    expect(component.postForm.get('content')?.value).toBe('');
    expect(component.postForm.get('author')?.value).toBe(mockUser.userName);
    expect(component.postForm.get('category')?.value).toBe(Category.TECHNOLOGY);
  });

  it('should submit valid form as draft', async () => {
    component.postForm.patchValue({
      title: 'Test Title',
      content: 'Test Content',
      category: Category.TECHNOLOGY
    });
    mockPostService.addPost.and.returnValue(of(mockNewPost));

    await component.onDraft();

    expect(mockPostService.addPost).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/posts']);
  });

  it('should submit valid form for review', async () => {
    component.postForm.patchValue({
      title: 'Test Title',
      content: 'Test Content',
      category: Category.TECHNOLOGY
    });
    const mockPendingPost = { ...mockNewPost, status: 'PENDING' };
    mockPostService.addPost.and.returnValue(of(mockPendingPost));

    await component.onSubmitForReview();

    expect(mockPostService.addPost).toHaveBeenCalled();
    const submitData = mockPostService.addPost.calls.first().args[0];
    expect(submitData.status).toBe('PENDING');
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/posts']);
  });

  it('should handle error when submitting form', async () => {
    component.postForm.patchValue({
      title: 'Test Title',
      content: 'Test Content',
      category: Category.TECHNOLOGY
    });
    const error = new Error('Test error');
    mockPostService.addPost.and.returnValue(throwError(() => error));

    await component.onDraft();

    expect(component.errorMessage).toBe(error.message);
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });
});

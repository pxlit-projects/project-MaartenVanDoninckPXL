import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { PostDraftItemComponent } from './post-draft-item.component';
import { Category, Post } from '../../../shared/models/post.model';
import { RouterTestingModule } from '@angular/router/testing';
import { provideRouter } from '@angular/router';

describe('PostDraftItemComponent', () => {
  let component: PostDraftItemComponent;
  let fixture: ComponentFixture<PostDraftItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CommonModule,
        PostDraftItemComponent,
      ], providers: [
        provideRouter([]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PostDraftItemComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render post data in template', () => {
    const testPost = new Post(
      1, 1, 'Test Title', 'Test Content', 'Test Author', 'DRAFT', Category.TECHNOLOGY, new Date()
    );
    component.post = testPost;
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h2')?.textContent).toContain('Test Title');
    expect(compiled.querySelector('.text-gray-500.text-sm.mb-2')?.textContent).toContain('Test Author');
    expect(compiled.querySelector('.text-gray-700.text-base')?.textContent).toContain('Test Content');
  });
});

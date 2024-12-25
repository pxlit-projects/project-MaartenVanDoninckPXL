import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostItemComponent } from './post-item.component';
import { Category, Post } from '../../../shared/models/post.model';
import { CommentService } from '../../../shared/services/comment.service';
import { of, throwError } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../shared/services/auth.service';
import { provideRouter } from '@angular/router';

describe('PostItemComponent', () => {
  let component: PostItemComponent;
  let fixture: ComponentFixture<PostItemComponent>;
  let mockCommentService: jasmine.SpyObj<CommentService>;
  let mockAuthService: jasmine.SpyObj<AuthService>;
  const mockUser = { userName: 'Author', role: 'redacteur' };
  const mockPost = new Post(1, 1, 'Post 1', 'Content 1', 'Author 1', 'POSTED', Category.TECHNOLOGY, new Date());
  const mockComments = [
    { id: 1, postId: 1, content: 'Comment 1', author: 'Author' },
    { id: 2, postId: 1, content: 'Comment 2', author: 'Author' }
  ];

  beforeEach(async () => {
    mockCommentService = jasmine.createSpyObj('CommentService', ['getCommentsByPostId']);
    mockAuthService = jasmine.createSpyObj('AuthService', ['getUser']);
    mockAuthService.getUser.and.returnValue(mockUser);

    await TestBed.configureTestingModule({
      imports: [
        CommonModule,
        FormsModule,
        PostItemComponent
      ],
      providers: [
        { provide: CommentService, useValue: mockCommentService },
        { provide: AuthService, useValue: mockAuthService },
        provideRouter([])
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PostItemComponent);
    component = fixture.componentInstance;
    component.post = mockPost;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should load comments on init', async () => {
    mockCommentService.getCommentsByPostId.and.returnValue(of(mockComments));
    await component.ngOnInit();
    expect(mockCommentService.getCommentsByPostId).toHaveBeenCalledWith(mockPost.id);
    expect(component.comments).toEqual(mockComments);
  });

  it('should toggle comments', () => {
    expect(component.showComments).toBeFalse();
    component.toggleComments();
    expect(component.showComments).toBeTrue();
  });

  it('should add a comment', async () => {
    mockCommentService.addComment = jasmine.createSpy().and.returnValue(of({}));
    mockCommentService.getCommentsByPostId.and.returnValue(of(mockComments));

    component.comment = 'New Comment';
    await component.addComment();

    expect(mockCommentService.addComment).toHaveBeenCalled();
    expect(component.comments).toEqual(mockComments);
    expect(component.comment).toBe('');
  });

  it('should handle error when adding a comment', async () => {
    mockCommentService.addComment = jasmine.createSpy().and.returnValue(throwError(() => new Error('Error')));
    component.comment = 'New Comment';

    await component.addComment();
    expect(mockCommentService.addComment).toHaveBeenCalled();
  });

  it('should update a comment', async () => {
    mockCommentService.updateComment = jasmine.createSpy().and.returnValue(of({}));
    mockCommentService.getCommentsByPostId.and.returnValue(of(mockComments));
    const editedComment = { ...mockComments[0], content: 'Edited Comment' };
    await component.updateComment(editedComment);

    expect(mockCommentService.updateComment).toHaveBeenCalledWith(editedComment);
    expect(component.comments).toEqual(mockComments);
    expect(component.editingComment).toBeUndefined();
  });

  it('should delete a comment', async () => {
    mockCommentService.deleteComment = jasmine.createSpy().and.returnValue(of({}));
    mockCommentService.getCommentsByPostId.and.returnValue(of([]));

    await component.deleteComment(mockComments[0].id);
    expect(mockCommentService.deleteComment).toHaveBeenCalledWith(mockComments[0].id);
    expect(component.comments).toEqual([]);
  });
});

import { TestBed } from '@angular/core/testing';
import { CommentService } from './comment.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { Comment } from '../models/comment.model';
import { CommentRequest } from '../models/comment-request.model';
import { provideHttpClient } from '@angular/common/http';

describe('CommentService', () => {
    let service: CommentService;
    let httpTestingController: HttpTestingController;
    const mockComments: Comment[] = [
        new Comment(1, 1, 'Comment 1', 'Author 1'),
        new Comment(2, 1, 'Comment 2', 'Author 2'),
        new Comment(3, 2, 'Comment 3', 'Author 3')
    ];
    const mockCommentRequest: CommentRequest = new CommentRequest(1, 'New Comment', 'Author 1');

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                CommentService,
                provideHttpClient(),
                provideHttpClientTesting(),
            ],
        });
        service = TestBed.inject(CommentService);
        httpTestingController = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpTestingController.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should add comment via POST', () => {
        const newComment = mockComments[0];

        service.addComment(mockCommentRequest).subscribe(comment => {
            expect(comment).toEqual(newComment);
        });

        const req = httpTestingController.expectOne(service.api);
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(mockCommentRequest);
        req.flush(newComment);
    });

    it('should get comments by post id via GET', () => {
        const postId = 1;
        const postComments = mockComments.filter(c => c.postId === postId);

        service.getCommentsByPostId(postId).subscribe(comments => {
            expect(comments).toEqual(postComments);
        });

        const req = httpTestingController.expectOne(`${service.api}/${postId}`);
        expect(req.request.method).toBe('GET');
        req.flush(postComments);
    });

    it('should update comment via PATCH', () => {
        const comment = mockComments[0];
        const commentRequest: CommentRequest = {
            postId: comment.postId,
            content: comment.content,
            author: comment.author
        };

        service.updateComment(comment).subscribe(updatedComment => {
            expect(updatedComment).toEqual(comment);
        });

        const req = httpTestingController.expectOne(`${service.api}/${comment.id}`);
        expect(req.request.method).toBe('PATCH');
        expect(req.request.body).toEqual(commentRequest);
        req.flush(comment);
    });

    it('should delete comment via DELETE', () => {
        const id = 1;

        service.deleteComment(id).subscribe(response => {
            expect(response).toBeUndefined();
        });

        const req = httpTestingController.expectOne(`${service.api}/${id}`);
        expect(req.request.method).toBe('DELETE');
    });

    it('should handle errors', () => {
        const errorMessage = 'Something went wrong';

        service.getCommentsByPostId(1).subscribe({
            error: error => {
                expect(error.message).toContain(errorMessage);
            }
        });

        const req = httpTestingController.expectOne(`${service.api}/1`);
        req.flush({ message: errorMessage }, { status: 500, statusText: 'Server Error' });
    });
});

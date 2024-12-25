import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PostPendingItemComponent } from './post-pending-item.component';
import { Category, Post } from '../../../shared/models/post.model';
import { ReviewService } from '../../../shared/services/review.service';
import { AuthService } from '../../../shared/services/auth.service';
import { PostService } from '../../../shared/services/post.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

describe('PostPendingItemComponent', () => {
    let component: PostPendingItemComponent;
    let fixture: ComponentFixture<PostPendingItemComponent>;
    let mockReviewService: jasmine.SpyObj<ReviewService>;
    let mockAuthService: jasmine.SpyObj<AuthService>;
    let mockPostService: jasmine.SpyObj<PostService>;
    let mockNotificationService: jasmine.SpyObj<NotificationService>;
    let mockRouter: jasmine.SpyObj<Router>;

    const mockUser = { userName: 'Author', role: 'hoofdredacteur' };
    const mockPost = new Post(1, 1, 'Post 1', 'Content 1', 'Author 1', 'PENDING', Category.TECHNOLOGY, new Date());
    const mockRejectionMessage = { author: 'Editor', content: 'Needs revision' };

    beforeEach(async () => {
        mockReviewService = jasmine.createSpyObj('ReviewService', ['getRejectionMessageById', 'createReview', 'deleteReview']);
        mockAuthService = jasmine.createSpyObj('AuthService', ['getUser']);
        mockPostService = jasmine.createSpyObj('PostService', ['submitPost']);
        mockNotificationService = jasmine.createSpyObj('NotificationService', ['notifyReviewUpdate']);
        mockRouter = jasmine.createSpyObj('Router', ['navigate']);

        mockAuthService.getUser.and.returnValue(mockUser);

        await TestBed.configureTestingModule({
            imports: [
                CommonModule,
                FormsModule,
                PostPendingItemComponent
            ],
            providers: [
                { provide: ReviewService, useValue: mockReviewService },
                { provide: AuthService, useValue: mockAuthService },
                { provide: PostService, useValue: mockPostService },
                { provide: NotificationService, useValue: mockNotificationService },
                { provide: Router, useValue: mockRouter }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(PostPendingItemComponent);
        component = fixture.componentInstance;
        component.post = mockPost;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should load rejection message on init for rejected post', async () => {
        component.post.status = 'REJECTED';
        mockReviewService.getRejectionMessageById.and.returnValue(of(mockRejectionMessage));

        await component.ngOnInit();

        expect(mockReviewService.getRejectionMessageById).toHaveBeenCalledWith(mockPost.reviewId);
        expect(component.rejectionMessage).toEqual(mockRejectionMessage);
    });

    it('should approve post', async () => {
        mockReviewService.createReview.and.returnValue(of(mockPost));
        const emitSpy = spyOn(component.statusChanged, 'emit');

        await component.approvePost();

        expect(mockReviewService.createReview).toHaveBeenCalled();
        expect(mockNotificationService.notifyReviewUpdate).toHaveBeenCalled();
        expect(emitSpy).toHaveBeenCalled();
    });

    it('should reject post with reason', async () => {
        mockReviewService.createReview.and.returnValue(of(mockPost));
        const emitSpy = spyOn(component.statusChanged, 'emit');
        component.rejectReason = 'Needs work';

        await component.rejectPost();

        expect(mockReviewService.createReview).toHaveBeenCalled();
        expect(mockNotificationService.notifyReviewUpdate).toHaveBeenCalled();
        expect(emitSpy).toHaveBeenCalled();
        expect(component.showError).toBeFalse();
    });

    it('should not reject post without reason', async () => {
        component.rejectReason = '';
        await component.rejectPost();

        expect(component.showError).toBeTrue();
        expect(mockReviewService.createReview).not.toHaveBeenCalled();
    });

    it('should submit post', async () => {
        mockPostService.submitPost.and.returnValue(of(mockPost));
        const emitSpy = spyOn(component.statusChanged, 'emit');

        await component.submitPost();

        expect(mockPostService.submitPost).toHaveBeenCalledWith(mockPost.id);
        expect(mockNotificationService.notifyReviewUpdate).toHaveBeenCalled();
        expect(emitSpy).toHaveBeenCalled();
    });

    it('should edit post', async () => {
        mockReviewService.deleteReview.and.returnValue(of(undefined));
        const emitSpy = spyOn(component.statusChanged, 'emit');

        await component.editPost();

        expect(mockReviewService.deleteReview).toHaveBeenCalledWith(mockPost.reviewId);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['/edit', mockPost.id]);
        expect(mockNotificationService.notifyReviewUpdate).toHaveBeenCalled();
        expect(emitSpy).toHaveBeenCalled();
    });

    it('should get rejection message', async () => {
        mockReviewService.getRejectionMessageById.and.returnValue(of(mockRejectionMessage));
        const emitSpy = spyOn(component.statusChanged, 'emit');

        await component.getRejectionMessage();

        expect(mockReviewService.getRejectionMessageById).toHaveBeenCalledWith(mockPost.reviewId);
        expect(component.rejectionMessage).toEqual(mockRejectionMessage);
        expect(mockNotificationService.notifyReviewUpdate).toHaveBeenCalled();
        expect(emitSpy).toHaveBeenCalled();
    });

    it('should handle error when getting rejection message', async () => {
        mockReviewService.getRejectionMessageById.and.returnValue(throwError(() => new Error('Error')));
        const consoleSpy = spyOn(console, 'error');

        await component.getRejectionMessage();

        expect(consoleSpy).toHaveBeenCalled();
    });
});

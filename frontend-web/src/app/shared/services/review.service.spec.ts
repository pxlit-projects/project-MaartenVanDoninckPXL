import { TestBed } from '@angular/core/testing';
import { ReviewService } from './review.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { Review } from '../models/review.model';
import { Post } from '../models/post.model';
import { Category } from '../models/post.model';

describe('ReviewService', () => {
    let service: ReviewService;
    let httpTestingController: HttpTestingController;

    const mockReview = new Review(1, 'Author 1', true, 'Good post');
    const mockPost = new Post(1, 1, 'Post 1', 'Content 1', 'Author 1', 'POSTED', Category.TECHNOLOGY, new Date());
    const mockRejectionMessage = { author: 'Author 1', content: 'Bad post' };

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                ReviewService,
                provideHttpClient(),
                provideHttpClientTesting(),
            ],
        });
        service = TestBed.inject(ReviewService);
        httpTestingController = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpTestingController.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should create review via POST', () => {
        service.createReview(mockReview).subscribe(post => {
            expect(post).toEqual(mockPost);
        });

        const req = httpTestingController.expectOne(service.api);
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(mockReview);
        req.flush(mockPost);
    });

    it('should get rejection message by id via GET', () => {
        const id = 1;

        service.getRejectionMessageById(id).subscribe(message => {
            return expect(message).toEqual(mockRejectionMessage);
        });

        const req = httpTestingController.expectOne(`${service.api}/rejection/${id}`);
        expect(req.request.method).toBe('GET');
        req.flush(mockRejectionMessage);
    });

    it('should delete review via DELETE', () => {
        const id = 1;

        service.deleteReview(id).subscribe(response => {
            expect(response).toBe(undefined);
        });

        const req = httpTestingController.expectOne(`${service.api}/${id}`);
        expect(req.request.method).toBe('DELETE');
    });

    it('should handle errors', () => {
        const errorMessage = 'Something went wrong';

        service.createReview(mockReview).subscribe({
            error: error => {
                expect(error.message).toContain(errorMessage);
            }
        });

        const req = httpTestingController.expectOne(service.api);
        req.flush({ message: errorMessage }, { status: 500, statusText: 'Server Error' });
    });
});

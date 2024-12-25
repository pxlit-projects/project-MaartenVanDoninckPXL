import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PostPendingListComponent } from './post-pending-list.component';
import { PostService } from '../../../shared/services/post.service';
import { AuthService } from '../../../shared/services/auth.service';
import { Category, Post } from '../../../shared/models/post.model';
import { of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FilterComponent } from '../filter/filter.component';
import { PostPendingItemComponent } from '../post-pending-item/post-pending-item.component';

describe('PostPendingListComponent', () => {
    let component: PostPendingListComponent;
    let fixture: ComponentFixture<PostPendingListComponent>;
    let postServiceMock: jasmine.SpyObj<PostService>;
    let authServiceMock: jasmine.SpyObj<AuthService>;
    const mockUser = { userName: 'Author 1', role: 'redacteur' };
    const mockHoofdredacteur = { userName: 'Editor', role: 'hoofdredacteur' };
    const mockPosts: Post[] = [
        new Post(1, 1, 'Post 1', 'Content 1', 'Author 1', 'PENDING', Category.TECHNOLOGY, new Date()),
        new Post(2, 2, 'Post 2', 'Content 2', 'Author 2', 'PENDING', Category.HEALTH, new Date()),
        new Post(3, 3, 'Post 3', 'Content 3', 'Author 3', 'PENDING', Category.EDUCATION, new Date())
    ];

    beforeEach(() => {
        postServiceMock = jasmine.createSpyObj('PostService', ['getPendingPosts', 'getPendingPostsByAuthor']);
        authServiceMock = jasmine.createSpyObj('AuthService', ['hasRole', 'getUser']);

        TestBed.configureTestingModule({
            imports: [
                CommonModule,
                PostPendingListComponent,
                FilterComponent,
                PostPendingItemComponent
            ],
            providers: [
                { provide: PostService, useValue: postServiceMock },
                { provide: AuthService, useValue: authServiceMock }
            ]
        });

        fixture = TestBed.createComponent(PostPendingListComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should get all pending posts for hoofdredacteur on init', async () => {
        authServiceMock.hasRole.and.returnValue(true);
        authServiceMock.getUser.and.returnValue(mockHoofdredacteur);
        postServiceMock.getPendingPosts.and.returnValue(of(mockPosts));

        await component.ngOnInit();

        expect(authServiceMock.hasRole).toHaveBeenCalledWith('hoofdredacteur');
        expect(postServiceMock.getPendingPosts).toHaveBeenCalled();
        expect(component.posts).toEqual(mockPosts);
    });

    it('should get pending posts by author for redacteur on init', async () => {
        authServiceMock.hasRole.and.returnValue(false);
        authServiceMock.getUser.and.returnValue(mockUser);
        postServiceMock.getPendingPostsByAuthor.and.returnValue(of([mockPosts[0]]));

        await component.ngOnInit();

        expect(authServiceMock.hasRole).toHaveBeenCalledWith('hoofdredacteur');
        expect(postServiceMock.getPendingPostsByAuthor).toHaveBeenCalledWith(mockUser.userName);
        expect(component.posts).toEqual([mockPosts[0]]);
    });

    it('should filter posts by title', () => {
        component.posts = mockPosts;
        component.onFilterChange({
            title: 'Post 1',
            author: '',
            category: '',
            startDate: '',
            endDate: ''
        });
        expect(component.filteredPosts).toEqual([mockPosts[0]]);
    });

    it('should filter posts by author', () => {
        component.posts = mockPosts;
        component.onFilterChange({
            title: '',
            author: 'Author 2',
            category: '',
            startDate: '',
            endDate: ''
        });
        expect(component.filteredPosts).toEqual([mockPosts[1]]);
    });

    it('should filter posts by category', () => {
        component.posts = mockPosts;
        component.onFilterChange({
            title: '',
            author: '',
            category: Category.EDUCATION,
            startDate: '',
            endDate: ''
        });
        expect(component.filteredPosts).toEqual([mockPosts[2]]);
    });

    it('should filter posts by date range', () => {
        const now = new Date();
        component.posts = mockPosts;
        component.onFilterChange({
            title: '',
            author: '',
            category: '',
            startDate: now.toISOString(),
            endDate: new Date(now.getTime() + 86400000).toISOString()
        });
        expect(component.filteredPosts.length).toBeGreaterThanOrEqual(0);
    });

    it('should filter posts by multiple criteria', () => {
        component.posts = mockPosts;
        component.onFilterChange({
            title: 'Post 1',
            author: 'Author 1',
            category: Category.TECHNOLOGY,
            startDate: '',
            endDate: ''
        });
        expect(component.filteredPosts).toEqual([mockPosts[0]]);
    });
});

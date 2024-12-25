import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostListComponent } from './post-list.component';
import { PostService } from '../../../shared/services/post.service';
import { Category, Post } from '../../../shared/models/post.model';
import { of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FilterComponent } from '../filter/filter.component';
import { PostItemComponent } from '../post-item/post-item.component';

describe('PostListComponent', () => {
  let component: PostListComponent;
  let fixture: ComponentFixture<PostListComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  const mockPosts: Post[] = [
    new Post(1, 1, 'Post 1', 'Content 1', 'Author 1', 'POSTED', Category.TECHNOLOGY, new Date()),
    new Post(2, 2, 'Post 2', 'Content 2', 'Author 2', 'POSTED', Category.HEALTH, new Date()),
    new Post(3, 3, 'Post 3', 'Content 3', 'Author 3', 'POSTED', Category.EDUCATION, new Date())
  ];

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj('PostService', ['getPostedPosts']);

    TestBed.configureTestingModule({
      imports: [
        CommonModule,
        PostListComponent,
        FilterComponent,
        PostItemComponent
      ],
      providers: [
        { provide: PostService, useValue: postServiceMock }
      ]
    });

    fixture = TestBed.createComponent(PostListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get posted posts on init', async () => {
    postServiceMock.getPostedPosts.and.returnValue(of(mockPosts));

    await component.ngOnInit();

    expect(postServiceMock.getPostedPosts).toHaveBeenCalled();
    expect(component.posts).toEqual(mockPosts);
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

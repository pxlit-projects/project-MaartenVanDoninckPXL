import { TestBed } from '@angular/core/testing';

import { PostService } from './post.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { Category, Post } from '../models/post.model';
import { provideHttpClient } from '@angular/common/http';

describe('PostService', () => {
  let service: PostService;
  let httpTestingController: HttpTestingController;
  const mockPosts: Post[] = [
    new Post(1, 1, 'Post 1', 'Content 1', 'Author 1', 'POSTED', Category.TECHNOLOGY, new Date()),
    new Post(2, 2, 'Post 2', 'Content 2', 'Author 2', 'POSTED', Category.HEALTH, new Date()),
    new Post(3, 3, 'Post 3', 'Content 3', 'Author 3', 'POSTED', Category.EDUCATION, new Date())
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PostService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });
    service = TestBed.inject(PostService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should add a post via POST', () => {
    const newPost = mockPosts[0];

    service.addPost(newPost).subscribe(post => {
      expect(post).toEqual(newPost);
    });

    const req = httpTestingController.expectOne(service.api);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newPost);
    req.flush(newPost);
  });

  it('should get all posts via GET', () => {
    service.getPosts().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(service.api);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should get posts in draft via GET', () => {
    service.getPostsInDraft().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(`${service.api}/draft`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should get posted posts via GET', () => {
    service.getPostedPosts().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(`${service.api}/posted`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should get post by id via GET', () => {
    const post = mockPosts[0];

    service.getPostById(post.id).subscribe(returnedPost => {
      expect(returnedPost).toEqual(post);
    });

    const req = httpTestingController.expectOne(`${service.api}/${post.id}`);
    expect(req.request.method).toBe('GET');
    req.flush(post);
  });

  it('should update post via PUT', () => {
    const post = mockPosts[0];

    service.updatePost(post).subscribe(updatedPost => {
      expect(updatedPost).toEqual(post);
    });

    const req = httpTestingController.expectOne(`${service.api}/${post.id}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(post);
    req.flush(post);
  });

  it('should submit post via PATCH', () => {
    const post = mockPosts[0];

    service.submitPost(post.id).subscribe(submittedPost => {
      expect(submittedPost).toEqual(post);
    });

    const req = httpTestingController.expectOne(`${service.api}/submit/${post.id}`);
    expect(req.request.method).toBe('PATCH');
    req.flush(post);
  });

  it('should handle errors', () => {
    const errorMessage = 'Something went wrong';

    service.getPosts().subscribe({
      error: error => {
        expect(error.message).toContain(errorMessage);
      }
    });

    const req = httpTestingController.expectOne(service.api);
    req.flush({ message: errorMessage }, { status: 500, statusText: 'Server Error' });
  });
});

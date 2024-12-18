import { inject, Injectable } from '@angular/core';
import { environment } from "../../../environments/environment.development";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Post } from "../models/post.model";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  api: string = environment.apiUrl + '/post/api/posts';
  http: HttpClient = inject(HttpClient);

  addPost(post: Post): Observable<Post> {
    return this.http.post<Post>(this.api, post).pipe(
      catchError(this.handleError)
    );
  }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.api).pipe(
      catchError(this.handleError)
    );
  }

  getPostsInDraft(): Observable<Post[]> {
    return this.http.get<Post[]>(this.api + '/draft').pipe(
      catchError(this.handleError)
    );
  }

  getPostsInDraftByAuthor(author: string): Observable<Post[]> {
    return this.http.get<Post[]>(this.api + '/draft/' + author).pipe(
      catchError(this.handleError)
    );
  }

  getApprovedPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.api + '/approved').pipe(
      catchError(this.handleError)
    );
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(this.api + '/' + id).pipe(
      catchError(this.handleError)
    );
  }

  updatePost(post: Post): Observable<Post> {
    return this.http.put<Post>(this.api + '/' + post.id, post).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Something went wrong: ${error.error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}

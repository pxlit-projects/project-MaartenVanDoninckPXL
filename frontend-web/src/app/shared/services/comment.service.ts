import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { Comment } from '../models/comment.model';
import { CommentRequest } from '../models/comment-request.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  api: string = environment.apiUrl + '/comment/api/comments';
  http: HttpClient = inject(HttpClient);

  getCommentsByPostId(postId: number) {
    return this.http.get<Comment[]>(this.api + '/' + postId).pipe(
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

  addComment(comment: CommentRequest) {
    return this.http.post(this.api, comment).pipe(
      catchError(this.handleError)
    );
  }
}

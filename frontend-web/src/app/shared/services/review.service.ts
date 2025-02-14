import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Review } from '../models/review.model';
import { catchError, Observable, throwError } from 'rxjs';
import { Post } from '../models/post.model';
import { RejectionMessage } from '../models/rejection-message.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  api: string = environment.apiUrl + '/review/api/reviews';
  http: HttpClient = inject(HttpClient);

  createReview(review: Review): Observable<Post> {
    return this.http.post<Post>(this.api, review).pipe(
      catchError(this.handleError)
    );
  }

  getRejectionMessageById(id: number): Observable<RejectionMessage> {
    return this.http.get<RejectionMessage>(this.api + '/rejection/' + id).pipe(
      catchError(this.handleError)
    );
  }

  deleteReview(id: number): Observable<void> {
    return this.http.delete<void>(this.api + '/' + id).pipe(
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

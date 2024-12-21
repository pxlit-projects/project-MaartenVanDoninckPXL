import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private reviewUpdateSource = new Subject<void>();
  reviewUpdate$ = this.reviewUpdateSource.asObservable();

  notifyReviewUpdate() {
    this.reviewUpdateSource.next();
  }
}

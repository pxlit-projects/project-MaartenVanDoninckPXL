import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../../../shared/models/post.model';
import { AuthService } from '../../../shared/services/auth.service';
import { PostService } from '../../../shared/services/post.service';
import { ReviewService } from '../../../shared/services/review.service';
import { Review } from '../../../shared/models/review.model';
import { delay } from 'rxjs';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-post-pending-item',
  standalone: true,
  imports: [],
  templateUrl: './post-pending-item.component.html',
  styleUrl: './post-pending-item.component.css'
})
export class PostPendingItemComponent {
  router: Router = inject(Router);
  postService: PostService = inject(PostService);
  reviewService: ReviewService = inject(ReviewService);
  authService: AuthService = inject(AuthService);
  notificationService: NotificationService = inject(NotificationService);

  @Input() post!: Post;
  @Output() statusChanged = new EventEmitter<void>();

  approvePost() {
    const review = new Review(
      this.post.id,
      this.authService.getUser()?.userName || '',
      true,
      'Post approved'
    );

    this.reviewService.createReview(review).pipe(
      delay(100)
    ).subscribe({
      next: () => {
        this.statusChanged.emit();
        this.notificationService.notifyReviewUpdate();
      },
      error: (error) => {
        console.error('Error creating review:', error);
      }
    });
  }

  rejectPost() {
    const review = new Review(
      this.post.id,
      this.authService.getUser()?.userName || '',
      false,
      'Post rejected'
    );

    this.reviewService.createReview(review).pipe(
      delay(100)
    ).subscribe({
      next: () => {
        this.statusChanged.emit();
        this.notificationService.notifyReviewUpdate();
      },
      error: (error) => {
        console.error('Error creating review:', error);
      }
    });
  }

  submitPost() {
    this.postService.submitPost(this.post.id).subscribe({
      next: () => {
        this.statusChanged.emit();
        this.notificationService.notifyReviewUpdate();
      },
      error: (error) => {
        console.error('Error updating post:', error);
      }
    });
  }
}

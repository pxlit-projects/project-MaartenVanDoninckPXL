import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../../../shared/models/post.model';
import { AuthService } from '../../../shared/services/auth.service';
import { PostService } from '../../../shared/services/post.service';
import { ReviewService } from '../../../shared/services/review.service';
import { Review } from '../../../shared/models/review.model';
import { delay } from 'rxjs';
import { NotificationService } from '../../../shared/services/notification.service';
import { FormsModule } from '@angular/forms';
import { RejectionMessage } from '../../../shared/models/rejection-message.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-post-pending-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './post-pending-item.component.html',
  styleUrl: './post-pending-item.component.css'
})
export class PostPendingItemComponent implements OnInit {
  router: Router = inject(Router);
  postService: PostService = inject(PostService);
  reviewService: ReviewService = inject(ReviewService);
  authService: AuthService = inject(AuthService);
  notificationService: NotificationService = inject(NotificationService);

  @Input() post!: Post;
  @Output() statusChanged = new EventEmitter<void>();
  rejectReason: string = '';
  showError: boolean = false;
  rejectionMessage: RejectionMessage | null = null;

  ngOnInit() {
    if (this.post.status === 'REJECTED') {
      this.reviewService.getRejectionMessageById(this.post.reviewId).subscribe({
        next: (message) => {
          this.rejectionMessage = message;
        },
        error: (error) => {
          console.error('Error getting rejection message:', error);
        }
      });
    }
  }

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
    if (!this.rejectReason.trim()) {
      this.showError = true;
      return;
    }

    this.showError = false;
    const review = new Review(
      this.post.id,
      this.authService.getUser()?.userName || '',
      false,
      this.rejectReason
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
    this.postService.submitPost(this.post.id).pipe(
      delay(100)
    ).subscribe({
      next: () => {
        this.statusChanged.emit();
        this.notificationService.notifyReviewUpdate();
      },
      error: (error) => {
        console.error('Error updating post:', error);
      }
    });
  }

  editPost() {
    this.reviewService.deleteReview(this.post.reviewId).pipe(
      delay(100)
    ).subscribe({
      next: () => {
        this.router.navigate(['/edit', this.post.id]);
        this.statusChanged.emit();
        this.notificationService.notifyReviewUpdate();
      },
      error: (error) => {
        console.error('Error deleting review:', error);
      }
    });
  }

  getRejectionMessage() {
    this.reviewService.getRejectionMessageById(this.post.reviewId).subscribe({
      next: (message) => {
        alert(message);
      },
      error: (error) => {
        console.error('Error getting rejection message:', error);
      }
    });
  }
}

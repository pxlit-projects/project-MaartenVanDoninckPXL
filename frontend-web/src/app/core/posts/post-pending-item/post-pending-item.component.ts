import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../../../shared/models/post.model';
import { AuthService } from '../../../shared/services/auth.service';
import { PostService } from '../../../shared/services/post.service';
import { ReviewService } from '../../../shared/services/review.service';
import { Review } from '../../../shared/models/review.model';
import { firstValueFrom } from 'rxjs';
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

  async ngOnInit(): Promise<void> {
    if (this.post.status === 'REJECTED') {
      try {
        this.rejectionMessage = await firstValueFrom(this.reviewService.getRejectionMessageById(this.post.reviewId));
      } catch (error) {
        console.error(error);
      }
    }
  }

  async approvePost() {
    const review = new Review(
      this.post.id,
      this.authService.getUser()?.userName || '',
      true,
      'Post approved'
    );
    try {
      await firstValueFrom(this.reviewService.createReview(review));
      await new Promise(resolve => setTimeout(resolve, 100));
      this.statusChanged.emit();
      this.notificationService.notifyReviewUpdate();
    } catch (error) {
      console.error(error);
    }
  }

  async rejectPost() {
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

    try {
      await firstValueFrom(this.reviewService.createReview(review));
      await new Promise(resolve => setTimeout(resolve, 100));
      this.statusChanged.emit();
      this.notificationService.notifyReviewUpdate();
    } catch (error) {
      console.error(error);
    }
  }

  async submitPost() {
    try {
      await firstValueFrom(this.postService.submitPost(this.post.id));
      this.statusChanged.emit();
      this.notificationService.notifyReviewUpdate();
    } catch (error) {
      console.error(error);
    }
  }

  async editPost() {
    try {
      await firstValueFrom(this.reviewService.deleteReview(this.post.reviewId));
      this.router.navigate(['/edit', this.post.id]);
      this.statusChanged.emit();
      this.notificationService.notifyReviewUpdate();
    } catch (error) {
      console.error(error);
    }
  }

  async getRejectionMessage() {
    try {
      this.rejectionMessage = await firstValueFrom(this.reviewService.getRejectionMessageById(this.post.reviewId));
      this.statusChanged.emit();
      this.notificationService.notifyReviewUpdate();
    } catch (error) {
      console.error(error);
    }
  }
}


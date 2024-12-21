import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from "@angular/router";
import { AuthService } from '../../shared/services/auth.service';
import { PostService } from '../../shared/services/post.service';
import { NotificationService } from '../../shared/services/notification.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit, OnDestroy {
  authService: AuthService = inject(AuthService);
  postService: PostService = inject(PostService);
  notificationService: NotificationService = inject(NotificationService);
  reviewsCount: number = 0;
  private subscription: Subscription;

  constructor() {
    this.subscription = this.notificationService.reviewUpdate$.subscribe(() => {
      this.updateReviewCount();
    });
  }

  ngOnInit() {
    this.updateReviewCount();
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private updateReviewCount() {
    if (this.authService.getUser()) {
      this.postService.getAmountOfReviewedPostsByAuthor(this.authService.getUser()!.userName)
        .subscribe(count => {
          this.reviewsCount = count;
        });
    }
  }

  logout(): void {
    this.authService.logout();
    this.notificationService.notifyReviewUpdate();
  }
}

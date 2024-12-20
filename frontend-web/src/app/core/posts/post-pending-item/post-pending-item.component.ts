import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../../../shared/models/post.model';
import { AuthService } from '../../../shared/services/auth.service';
import { PostService } from '../../../shared/services/post.service';

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
  authService: AuthService = inject(AuthService);
  @Input() post!: Post;
  @Output() statusChanged = new EventEmitter<void>();

  approvePost() {
    this.postService.approvePost(this.post.id).subscribe({
      next: () => {
        this.statusChanged.emit();
      },
      error: (error) => {
        console.error('Error updating post:', error);
      }
    });
  }

  rejectPost() {
    this.postService.rejectPost(this.post.id).subscribe({
      next: () => {
        this.statusChanged.emit();
      },
      error: (error) => {
        console.error('Error updating post:', error);
      }
    });
  }
}

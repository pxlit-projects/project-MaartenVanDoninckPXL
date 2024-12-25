import { Component, inject, Input } from '@angular/core';
import { Post } from "../../../shared/models/post.model";
import { CommentService } from '../../../shared/services/comment.service';
import { Comment } from '../../../shared/models/comment.model';
import { CommonModule } from '@angular/common';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../shared/services/auth.service';
import { CommentRequest } from '../../../shared/models/comment-request.model';
import { firstValueFrom } from 'rxjs';
@Component({
  selector: 'app-post-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './post-item.component.html',
  styleUrl: './post-item.component.css',
  animations: [
    trigger('commentsAnimation', [
      state('hidden', style({ height: '0', opacity: 0, overflow: 'hidden' })),
      state('visible', style({ height: '*', opacity: 1 })),
      transition('hidden => visible', [animate('200ms ease-out')]),
      transition('visible => hidden', [animate('200ms ease-in')]),
    ]),
  ]
})
export class PostItemComponent {
  @Input() post!: Post;
  commentService: CommentService = inject(CommentService);
  authService: AuthService = inject(AuthService);

  comments: Comment[] = [];
  showComments = false;
  comment: string = '';
  showError: boolean = false;
  user? = this.authService.getUser();
  editingComment?: Comment;

  async ngOnInit(): Promise<void> {
    try {
      this.comments = await firstValueFrom(this.commentService.getCommentsByPostId(this.post.id));
    } catch (error) {
      console.error(error);
    }
  }

  toggleComments() {
    this.showComments = !this.showComments;
  }

  async addComment() {
    if (!this.comment) {
      this.showError = true;
      return;
    }

    const commentRequest: CommentRequest = {
      postId: this.post.id,
      content: this.comment,
      author: this.user!.userName
    }

    try {
      await firstValueFrom(this.commentService.addComment(commentRequest));
      this.comments = await firstValueFrom(this.commentService.getCommentsByPostId(this.post.id));
      this.comment = '';
      this.showError = false;
    } catch (error) {
      console.error(error);
    }
  }

  startEdit(comment: Comment) {
    this.editingComment = { ...comment };
  }

  cancelEdit() {
    this.editingComment = undefined;
  }

  async updateComment(comment: Comment) {
    try {
      await firstValueFrom(this.commentService.updateComment(comment));
      this.comments = await firstValueFrom(this.commentService.getCommentsByPostId(this.post.id));
      this.editingComment = undefined;
    } catch (error) {
      console.error(error);
    }
  }

  async deleteComment(id: number) {
    try {
      await firstValueFrom(this.commentService.deleteComment(id));
      this.comments = await firstValueFrom(this.commentService.getCommentsByPostId(this.post.id));
    } catch (error) {
      console.error(error);
    }
  }
}

import { Component, inject, OnInit } from '@angular/core';
import { PostService } from '../../../shared/services/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Post, Category } from '../../../shared/models/post.model';

@Component({
  selector: 'app-edit-post',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './edit-post.component.html',
  styleUrl: './edit-post.component.css'
})
export class EditPostComponent implements OnInit {
  postService: PostService = inject(PostService);
  route: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  fb: FormBuilder = inject(FormBuilder);

  categories = Object.values(Category);

  postForm = this.fb.group({
    id: [{ value: 0, disabled: true }],
    reviewId: [0],
    title: ['', Validators.required],
    content: ['', Validators.required],
    author: ['', Validators.required],
    status: [{ value: '', disabled: true }],
    category: ['', Validators.required]
  });

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Route ID:', id);
    if (id) {
      this.postService.getPostById(id).subscribe({
        next: (post) => {
          console.log('Fetched post:', post);
          this.postForm.patchValue(post);
        },
        error: (error) => {
          console.error('Error fetching post:', error);
        }
      });
    } else {
      console.error('Invalid post ID');
      this.router.navigate(['/draft']);
    }
  }

  onUpdate(): void {
    if (this.postForm.valid) {
      const formValue = this.postForm.getRawValue();
      const updatedPost = new Post(
        formValue.id ?? 0,
        formValue.reviewId ?? 0,
        formValue.title ?? '',
        formValue.content ?? '',
        formValue.author ?? '',
        formValue.status ?? '',
        formValue.category as Category ?? ''
      );
      this.postService.updatePost(updatedPost).subscribe({
        next: () => {
          this.router.navigate(['/draft']);
        },
        error: (error) => {
          console.error('Error updating post:', error);
        }
      });
    }
  }

  onSubmitForReview(): void {
    if (this.postForm.valid) {
      const formValue = this.postForm.getRawValue();
      const updatedPost = new Post(
        formValue.id ?? 0,
        formValue.reviewId ?? 0,
        formValue.title ?? '',
        formValue.content ?? '',
        formValue.author ?? '',
        'PENDING',
        formValue.category as Category ?? ''
      );
      this.postService.updatePost(updatedPost).subscribe({
        next: () => {
          this.router.navigate(['/draft']);
        },
        error: (error) => {
          console.error('Error updating post:', error);
        }
      });
    }
  }
}

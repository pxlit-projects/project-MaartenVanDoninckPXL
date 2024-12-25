import { Component, inject, OnInit } from '@angular/core';
import { PostService } from '../../../shared/services/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Post, Category } from '../../../shared/models/post.model';
import { firstValueFrom } from 'rxjs';

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
    category: ['', Validators.required],
    createdOn: [{ value: new Date(), disabled: true }]
  });

  async ngOnInit(): Promise<void> {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Route ID:', id);
    if (id) {
      try {
        const post = await firstValueFrom(this.postService.getPostById(id));
        this.postForm.patchValue(post);
      } catch (error) {
        console.error(error);
        this.router.navigate(['/draft']);
      }
    } else {
      console.error('Invalid post ID');
      this.router.navigate(['/draft']);
    }
  }

  async onUpdate(): Promise<void> {
    if (this.postForm.valid) {
      const formValue = this.postForm.getRawValue();
      const updatedPost = new Post(
        formValue.id ?? 0,
        formValue.reviewId ?? 0,
        formValue.title ?? '',
        formValue.content ?? '',
        formValue.author ?? '',
        formValue.status ?? '',
        formValue.category as Category ?? '',
        formValue.createdOn ?? new Date()
      );
      try {
        await firstValueFrom(this.postService.updatePost(updatedPost));
        this.router.navigate(['/draft']);
      } catch (error) {
        console.error('Error updating post:', error);
      }
    }
  }

  async onSubmitForReview(): Promise<void> {
    if (this.postForm.valid) {
      const formValue = this.postForm.getRawValue();
      const updatedPost = new Post(
        formValue.id ?? 0,
        formValue.reviewId ?? 0,
        formValue.title ?? '',
        formValue.content ?? '',
        formValue.author ?? '',
        'PENDING',
        formValue.category as Category ?? '',
        formValue.createdOn ?? new Date()
      );
      try {
        await firstValueFrom(this.postService.updatePost(updatedPost));
        this.router.navigate(['/draft']);
      } catch (error) {
        console.error('Error updating post:', error);
      }
    }
  }
}

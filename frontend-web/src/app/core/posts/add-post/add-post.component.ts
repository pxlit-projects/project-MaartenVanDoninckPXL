import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { PostService } from "../../../shared/services/post.service";
import { Category, Post } from "../../../shared/models/post.model";
import { Router } from "@angular/router";
import { AuthService } from '../../../shared/services/auth.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-add-post',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-post.component.html',
  styleUrl: './add-post.component.css'
})
export class AddPostComponent {
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);

  categories = Object.values(Category);

  fb: FormBuilder = inject(FormBuilder);
  postForm: FormGroup = this.fb.group({
    title: ['', Validators.required],
    content: ['', Validators.required],
    author: [this.authService.getUser()!.userName, Validators.required],
    status: ['DRAFT', Validators.required],
    category: [Category.TECHNOLOGY, Validators.required],
  });

  errorMessage: string | null = null;

  async onSubmitForReview() {
    this.errorMessage = null;
    if (this.postForm.valid) {
      const newPost: Post = {
        ...this.postForm.value,
        status: 'PENDING'
      };
      try {
        await firstValueFrom(this.postService.addPost(newPost));
        this.postForm.reset();
        this.router.navigate(['/posts']);
      } catch (error: any) {
        this.errorMessage = error.message;
      }
    } else {
      this.errorMessage = 'Please fill out the form before submitting';
    }
  }

  async onDraft() {
    this.errorMessage = null;
    if (this.postForm.valid) {
      const newPost: Post = {
        ...this.postForm.value,
        status: 'DRAFT'
      };
      try {
        await firstValueFrom(this.postService.addPost(newPost));
        this.postForm.reset();
        this.router.navigate(['/posts']);
      } catch (error: any) {
        this.errorMessage = error.message;
      }
    } else {
      this.errorMessage = 'Please fill out the form before saving as draft';
    }
  }

  isFormInvalid() {
    return this.postForm.invalid;
  }
}

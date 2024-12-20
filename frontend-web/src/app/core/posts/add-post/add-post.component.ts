import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {PostService} from "../../../shared/services/post.service";
import {Category, Post} from "../../../shared/models/post.model";
import {Router} from "@angular/router";
import { AuthService } from '../../../shared/services/auth.service';

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

  onSubmitForReview() {
    this.errorMessage = null;
    if (this.postForm.valid) {
      const newPost: Post = {
        ...this.postForm.value,
        status: 'PENDING'
      };
      this.postService.addPost(newPost).subscribe({
        next: () => {
          this.postForm.reset();
          this.router.navigate(['/posts']);
        },
        error: (error) => this.errorMessage = error.message
      });
    } else {
      this.errorMessage = 'Please fill out the form before submitting';
    }
  }

  onDraft() {
    this.errorMessage = null;
    if (this.postForm.valid) {
      const newPost: Post = {
        ...this.postForm.value,
        status: 'DRAFT'
      };
      this.postService.addPost(newPost).subscribe({
        next: () => {
          this.postForm.reset();
          this.router.navigate(['/posts']);
        },
        error: (error) => this.errorMessage = error.message
      });
    } else {
      this.errorMessage = 'Please fill out the form before saving as draft';
    }
  }

  isFormInvalid() {
    return this.postForm.invalid;
  }
}

import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {PostService} from "../../../shared/services/post.service";
import {Post} from "../../../shared/models/post.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-post',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-post.component.html',
  styleUrl: './add-post.component.css'
})
export class AddPostComponent {
  postService: PostService = inject(PostService);
  router: Router = inject(Router);

  fb: FormBuilder = inject(FormBuilder);
  postForm: FormGroup = this.fb.group({
    title: ['', Validators.required],
    content: ['', Validators.required],
    author: ['', Validators.required],
    status: ['PENDING', Validators.required]
  });

  errorMessage: string | null = null;

  onSubmit() {
    if (this.postForm.valid) {
      const newPost: Post = {
        ...this.postForm.value
      };
      this.postService.addPost(newPost).subscribe({
        next: () => {
          this.postForm.reset();
          this.router.navigate(['/posts']);
        },
        error: (error) => this.errorMessage = error.message
      });
    }
  }

  onDraft() {
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
    }
  }

  isFormInvalid() {
    return this.postForm.invalid;
  }
}

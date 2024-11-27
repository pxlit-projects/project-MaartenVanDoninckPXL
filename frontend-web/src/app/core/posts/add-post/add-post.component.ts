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
    body: ['', Validators.required],
    author: ['', Validators.required],
  });

  onSubmit() {
    const newPost: Post = {
      ...this.postForm.value
    };
    this.postService.addPost(newPost).subscribe(post => {
      this.postForm.reset();
      this.router.navigate(['/posts']);
    });
  }

  isFormInvalid() {
    return this.postForm.invalid;
  }
}

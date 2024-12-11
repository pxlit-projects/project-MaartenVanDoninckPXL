import { Component, inject, OnInit } from '@angular/core';
import { PostService } from '../../../shared/services/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Post } from '../../../shared/models/post.model';

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

  postForm = this.fb.group({
    id: [{ value: 0, disabled: true }],
    title: ['', Validators.required],
    content: ['', Validators.required],
    author: ['', Validators.required],
    status: ['', Validators.required]
  });

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.postService.getPostById(id).subscribe({
        next: (post) => {
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

  onSubmit(): void {
    if (this.postForm.valid) {
      const updatedPost = this.postForm.getRawValue() as Post;
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

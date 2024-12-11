import {Component, inject, OnInit} from '@angular/core';
import {FilterComponent} from "../filter/filter.component";
import {PostItemComponent} from "../post-item/post-item.component";
import {PostService} from "../../../shared/services/post.service";
import {Post} from "../../../shared/models/post.model";

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [
    FilterComponent,
    PostItemComponent
  ],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit {
  postService: PostService = inject(PostService);
  posts: Post[] = [];

  ngOnInit() {
    this.postService.getApprovedPosts().subscribe((data: Post[]) => {
      this.posts = data;
    });
  }
}

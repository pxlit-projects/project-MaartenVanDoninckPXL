import { Component, inject, OnInit } from '@angular/core';
import { FilterComponent } from "../filter/filter.component";
import { PostItemComponent } from "../post-item/post-item.component";
import { PostService } from "../../../shared/services/post.service";
import { Post } from "../../../shared/models/post.model";

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [
    FilterComponent,
    PostItemComponent
  ],
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
  postService: PostService = inject(PostService);
  posts: Post[] = [];
  filteredPosts: Post[] = [];

  ngOnInit() {
    this.postService.getPostedPosts().subscribe((data: Post[]) => {
      this.posts = data;
      this.filteredPosts = data;
    });
  }

  onFilterChange(filterCriteria: any) {
    this.filteredPosts = this.posts.filter(post => {
      const matchesTitle = post.title.includes(filterCriteria.title);
      const matchesAuthor = post.author.includes(filterCriteria.author);
      const matchesCategory =
        !filterCriteria.category || post.category === filterCriteria.category;

      return matchesTitle && matchesAuthor && matchesCategory;
    });
  }
}

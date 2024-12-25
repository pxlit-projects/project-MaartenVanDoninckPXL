import { Component, inject, OnInit } from '@angular/core';
import { FilterComponent } from "../filter/filter.component";
import { PostItemComponent } from "../post-item/post-item.component";
import { PostService } from "../../../shared/services/post.service";
import { Post } from "../../../shared/models/post.model";
import { firstValueFrom } from 'rxjs';

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

  async ngOnInit(): Promise<void> {
    try {
      this.posts = await firstValueFrom(this.postService.getPostedPosts());
      this.filteredPosts = this.posts;
    } catch (error) {
      console.error(error);
    }
  }

  onFilterChange(filterCriteria: any) {
    this.filteredPosts = this.posts.filter(post => {
      const postDate = new Date(post.createdOn);
      const startDate = filterCriteria.startDate ? new Date(filterCriteria.startDate) : null;
      const endDate = filterCriteria.endDate ? new Date(filterCriteria.endDate) : null;

      const matchesTitle = post.title.toLowerCase().includes(filterCriteria.title.toLowerCase());
      const matchesAuthor = post.author.toLowerCase().includes(filterCriteria.author.toLowerCase());
      const matchesCategory = !filterCriteria.category || post.category === filterCriteria.category;
      const matchesDateRange = (!startDate || postDate >= startDate) &&
        (!endDate || postDate <= endDate);

      return matchesTitle && matchesAuthor && matchesCategory && matchesDateRange;
    });
  }
}

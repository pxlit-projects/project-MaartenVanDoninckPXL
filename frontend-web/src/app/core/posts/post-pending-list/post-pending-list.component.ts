import { Component, inject } from '@angular/core';
import { PostService } from '../../../shared/services/post.service';
import { AuthService } from '../../../shared/services/auth.service';
import { Post } from '../../../shared/models/post.model';
import { PostPendingItemComponent } from "../post-pending-item/post-pending-item.component";
import { FilterComponent } from "../filter/filter.component";

@Component({
  selector: 'app-post-pending-list',
  standalone: true,
  imports: [PostPendingItemComponent, FilterComponent],
  templateUrl: './post-pending-list.component.html',
  styleUrl: './post-pending-list.component.css'
})
export class PostPendingListComponent {
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);
  posts: Post[] = [];
  filteredPosts: Post[] = [];

  ngOnInit() {
    if (this.authService.hasRole('hoofdredacteur')) {
      this.postService.getPendingPosts().subscribe((data: Post[]) => {
        this.posts = data;
        this.filteredPosts = data;
      });
    } else {
      this.postService.getPendingPostsByAuthor(this.authService.getUser()!.userName).subscribe((data: Post[]) => {
        this.posts = data;
        this.filteredPosts = data;
      });
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

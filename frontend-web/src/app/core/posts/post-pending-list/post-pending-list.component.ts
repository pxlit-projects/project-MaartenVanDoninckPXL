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
      const matchesTitle = post.title.includes(filterCriteria.title);
      const matchesAuthor = post.author.includes(filterCriteria.author);
      const matchesCategory =
        !filterCriteria.category || post.category === filterCriteria.category;

      return matchesTitle && matchesAuthor && matchesCategory;
    });
  }
}

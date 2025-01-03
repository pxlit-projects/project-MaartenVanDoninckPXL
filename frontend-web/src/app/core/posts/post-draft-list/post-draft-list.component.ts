import { Component, inject } from '@angular/core';
import { FilterComponent } from "../filter/filter.component";
import { PostService } from '../../../shared/services/post.service';
import { Post } from '../../../shared/models/post.model';
import { PostDraftItemComponent } from "../post-draft-item/post-draft-item.component";
import { AuthService } from '../../../shared/services/auth.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-post-draft-list',
  standalone: true,
  imports: [FilterComponent, PostDraftItemComponent],
  templateUrl: './post-draft-list.component.html',
  styleUrls: ['./post-draft-list.component.css']
})
export class PostDraftListComponent {
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);
  posts: Post[] = [];
  filteredPosts: Post[] = [];

  async ngOnInit(): Promise<void> {
    try {
      if (this.authService.hasRole('hoofdredacteur')) {
        this.posts = await firstValueFrom(this.postService.getPostsInDraft());
      } else {
        this.posts = await firstValueFrom(
          this.postService.getPostsInDraftByAuthor(this.authService.getUser()!.userName)
        );
      }
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

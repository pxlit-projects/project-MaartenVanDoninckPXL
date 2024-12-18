import { Component, inject } from '@angular/core';
import { FilterComponent } from "../filter/filter.component";
import { PostService } from '../../../shared/services/post.service';
import { Post } from '../../../shared/models/post.model';
import { PostDraftItemComponent } from "../post-draft-item/post-draft-item.component";
import { AuthService } from '../../../shared/services/auth.service';

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

  ngOnInit() {
    if (this.authService.hasRole('hoofdredacteur')) {
      this.postService.getPostsInDraft().subscribe((data: Post[]) => {
        this.posts = data;
        this.filteredPosts = data;
      });
    } else {
      this.postService.getPostsInDraftByAuthor(this.authService.getUser()!.userName).subscribe((data: Post[]) => {
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

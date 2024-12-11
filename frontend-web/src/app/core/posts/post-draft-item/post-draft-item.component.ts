import { Component, inject, Input } from '@angular/core';
import { Post } from '../../../shared/models/post.model';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-post-draft-item',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './post-draft-item.component.html',
  styleUrl: './post-draft-item.component.css'
})
export class PostDraftItemComponent {
  router: Router = inject(Router);
  @Input() post!: Post;
}

import { Component, Input } from '@angular/core';
import { Post } from '../../../shared/models/post.model';

@Component({
  selector: 'app-post-draft-item',
  standalone: true,
  imports: [],
  templateUrl: './post-draft-item.component.html',
  styleUrl: './post-draft-item.component.css'
})
export class PostDraftItemComponent {
  @Input() post!: Post;
}

import {Component, Input} from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {Post} from "../../../shared/models/post.model";

@Component({
  selector: 'app-post-item',
  standalone: true,
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './post-item.component.html',
  styleUrl: './post-item.component.css'
})
export class PostItemComponent {
  @Input() post!: Post;
}

import { Component } from '@angular/core';
import {FilterComponent} from "../filter/filter.component";
import {PostItemComponent} from "../post-item/post-item.component";

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
export class PostListComponent {

}

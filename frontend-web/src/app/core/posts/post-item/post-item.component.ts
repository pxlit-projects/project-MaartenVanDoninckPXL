import { Component } from '@angular/core';
import {NgOptimizedImage} from "@angular/common";

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

}

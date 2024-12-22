import { Component, EventEmitter, Output } from '@angular/core';
import { Category } from '../../../shared/models/post.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-filter',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {
  title: string = '';
  author: string = '';
  category: Category | '' = '';
  startDate: string = '';
  endDate: string = '';
  categories = Object.values(Category);

  @Output() filterChanged = new EventEmitter<any>();

  onFilterChange() {
    this.filterChanged.emit({
      title: this.title,
      author: this.author,
      startDate: this.startDate,
      endDate: this.endDate,
      category: this.category
    });
  }
}

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FilterComponent } from './filter.component';
import { FormsModule } from '@angular/forms';
import { Category } from '../../../shared/models/post.model';

describe('FilterComponent', () => {
    let component: FilterComponent;
    let fixture: ComponentFixture<FilterComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [FormsModule, FilterComponent]
        }).compileComponents();

        fixture = TestBed.createComponent(FilterComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should emit filterChanged when onFilterChange is called', () => {
        spyOn(component.filterChanged, 'emit');

        component.title = 'Title';
        component.author = 'Author';
        component.category = Category.TECHNOLOGY;
        component.startDate = new Date().toISOString();
        const endDate = new Date();
        endDate.setDate(endDate.getDate() + 7);
        component.endDate = endDate.toISOString();

        component.onFilterChange();

        expect(component.filterChanged.emit).toHaveBeenCalledWith({
            title: 'Title',
            author: 'Author',
            startDate: component.startDate,
            endDate: component.endDate,
            category: Category.TECHNOLOGY
        });
    });
});
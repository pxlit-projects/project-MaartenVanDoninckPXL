import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostPendingItemComponent } from './post-pending-item.component';

describe('PostPendingItemComponent', () => {
  let component: PostPendingItemComponent;
  let fixture: ComponentFixture<PostPendingItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostPendingItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostPendingItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

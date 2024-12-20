import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostPendingListComponent } from './post-pending-list.component';

describe('PostPendingListComponent', () => {
  let component: PostPendingListComponent;
  let fixture: ComponentFixture<PostPendingListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostPendingListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostPendingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

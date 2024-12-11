import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostDraftItemComponent } from './post-draft-item.component';

describe('PostDraftItemComponent', () => {
  let component: PostDraftItemComponent;
  let fixture: ComponentFixture<PostDraftItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostDraftItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostDraftItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

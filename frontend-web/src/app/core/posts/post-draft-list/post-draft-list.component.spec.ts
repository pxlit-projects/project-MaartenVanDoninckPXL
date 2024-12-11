import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostDraftListComponent } from './post-draft-list.component';

describe('PostDraftListComponent', () => {
  let component: PostDraftListComponent;
  let fixture: ComponentFixture<PostDraftListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostDraftListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostDraftListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

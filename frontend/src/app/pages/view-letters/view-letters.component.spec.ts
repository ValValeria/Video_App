import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewLettersComponent } from './view-letters.component';

describe('ViewLettersComponent', () => {
  let component: ViewLettersComponent;
  let fixture: ComponentFixture<ViewLettersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewLettersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewLettersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

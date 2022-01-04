import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterComponent } from './footer.component';
import {ElementRef, ViewRef} from "@angular/core";

describe('FooterComponent', () => {
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FooterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterComponent);
    const view: ElementRef<any> = fixture.componentRef.location;

    expect(view.nativeElement.querySelector('.footer__content')).toBeDefined();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

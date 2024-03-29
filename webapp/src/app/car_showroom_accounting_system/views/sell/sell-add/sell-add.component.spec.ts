/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SellAddComponent } from './sell-add.component';

describe('SellAddComponent', () => {
  let component: SellAddComponent;
  let fixture: ComponentFixture<SellAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SellAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SellAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

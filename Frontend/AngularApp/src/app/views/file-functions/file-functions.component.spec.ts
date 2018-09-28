import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileFunctionsComponent } from './file-functions.component';

describe('FileFunctionsComponent', () => {
  let component: FileFunctionsComponent;
  let fixture: ComponentFixture<FileFunctionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileFunctionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileFunctionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

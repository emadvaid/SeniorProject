import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileFunctionsRedoComponent } from './file-functions-redo.component';

describe('FileFunctionsRedoComponent', () => {
  let component: FileFunctionsRedoComponent;
  let fixture: ComponentFixture<FileFunctionsRedoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileFunctionsRedoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileFunctionsRedoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

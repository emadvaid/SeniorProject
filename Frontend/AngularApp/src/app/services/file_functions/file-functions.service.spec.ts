import { TestBed } from '@angular/core/testing';

import { FileFunctionsService } from './file-functions.service';

describe('FileFunctionsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FileFunctionsService = TestBed.get(FileFunctionsService);
    expect(service).toBeTruthy();
  });
});

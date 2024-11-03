import { TestBed } from '@angular/core/testing';

import { SeService } from './se.service';

describe('SeService', () => {
  let service: SeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

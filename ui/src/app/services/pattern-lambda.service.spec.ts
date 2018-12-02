import { TestBed } from '@angular/core/testing';

import { PatternLambdaService } from './pattern-lambda.service';

describe('PatternLambdaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PatternLambdaService = TestBed.get(PatternLambdaService);
    expect(service).toBeTruthy();
  });
});

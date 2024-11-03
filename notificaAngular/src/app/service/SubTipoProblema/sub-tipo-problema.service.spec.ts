import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SubTipoProblemaService } from './sub-tipo-problema.service';

describe('SubTipoProblemaService', () => {
  let service: SubTipoProblemaService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(SubTipoProblemaService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should list subtipo', () => {
    service.listarporsubtipo('tipoProblema').subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/subtipoproblemas/listar/tipoProblema');
    expect(req.request.method).toEqual('GET');
  });
});

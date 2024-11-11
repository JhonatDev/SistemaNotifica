import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TicktsService } from './tickts.service';

describe('TicktsService', () => {
  let service: TicktsService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(TicktsService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should list tickts', () => {
    service.listar().subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/listar');
    expect(req.request.method).toEqual('GET');
  });

  it('should create tickts', () => {
    service.criar({}).subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/criar');
    expect(req.request.method).toEqual('POST');
  });

  it('should atualizar tickts', () => {
    service.atualizar(1, {
      id: 0,
      resumoProblema: '',
      local: '',
      tipoProblema: '',
      outrovtipoProblema: '',
      subtipoProblema: '',
      outroSubtipoProblema: '',
      status: '',
      dataCriacao: '',
      dataSolucao: '',
      caminhoFoto: '',
      raAluno: '',
      outroLocal: undefined,
      funcionarioResponsavel: ''

    }).subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/atualizar/1');
    expect(req.request.method).toEqual('PUT');
  });

  it('should delete tickts', () => {
    service.deletar(1).subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/deletar/1');
    expect(req.request.method).toEqual('DELETE');
  });

  it('should start tickts', () => {
    service.iniciar(1, 'funcionarioResponsavel').subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/iniciar/funcionarioResponsavel/1');
    expect(req.request.method).toEqual('PUT');
  });

  it('should reopen tickts', () => {
    service.reabrir(1).subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/voltarAberto/1');
    expect(req.request.method).toEqual('PUT');
  });

  it('should finish tickts', () => {
    service.finalizar(1).subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/solucionar/1');
    expect(req.request.method).toEqual('PUT');
  });

  it('should cancel tickts', () => {
    service.cancelar(1).subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/cancelar/1');
    expect(req.request.method).toEqual('PUT');
  });

  it('should list tickts by status', () => {
    service.listarPorStatus('status').subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/listarPorStatus/status');
    expect(req.request.method).toEqual('GET');
  });

  it('should list tickts by user e status', () => {
    service.listarPorUsuarioStatus('Usuario', 'status').subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/buscarPorRaEStatus/Usuario/status');
    expect(req.request.method).toEqual('GET');
  });

  it('should list tickts by user', () => {
    service.listarPorUsuario('Usuario').subscribe();

    const req = httpTestingController.expectOne('http://localhost:8080/tickets/buscarPorRa/Usuario');
    expect(req.request.method).toEqual('GET');
  });

  afterEach(() => {
    httpTestingController.verify();
  });

});

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tickts } from '../../models/tickts/tickts';

@Injectable({
  providedIn: 'root'
})
export class TicktsService {


  constructor(private http: HttpClient) {}

  listar(): Observable<any> {
    return this.http.get('http://localhost:8080/tickets/listar');
  }

  criar(tickts: any): Observable<any> {
    return this.http.post('http://localhost:8080/tickets/criar', tickts);
  }

  atualizar (id: number, tickts: Tickts): Observable<any> {
    return this.http.put(`http://localhost:8080/tickets/atualizar/${id}`, tickts);
  }

  deletar(id: number): Observable<string> {
    return this.http.delete<string>(`http://localhost:8080/tickets/deletar/${id}`);
  }

  iniciar(id: number, funcionarioResponsavel: string): Observable<any> {
    return this.http.put(`http://localhost:8080/tickets/iniciar/${funcionarioResponsavel}/${id}`, {});
  }

  reabrir(id: number): Observable<any> {
    return this.http.put(`http://localhost:8080/tickets/voltarAberto/${id}`, {});
  }

  finalizar(id: number): Observable<any> {
    return this.http.put(`http://localhost:8080/tickets/solucionar/${id}`, {});
  }

  cancelar(id: number): Observable<any> {
    return this.http.put(`http://localhost:8080/tickets/cancelar/${id}`, {});
  }

  listarPorStatus(status: string): Observable<any> {
    return this.http.get(`http://localhost:8080/tickets/listarPorStatus/${status}`);
  }

  listarPorUsuarioStatus(Usuario: string, status: string): Observable<any> {
    return this.http.get(`http://localhost:8080/tickets/buscarPorRaEStatus/${Usuario}/${status}`);
  }

  listarPorUsuario(Usuario: string): Observable<any> {
    return this.http.get(`http://localhost:8080/tickets/buscarPorRa/${Usuario}`);
  }

  
}

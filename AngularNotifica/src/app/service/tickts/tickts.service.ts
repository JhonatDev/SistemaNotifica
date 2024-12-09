import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tickts } from '../../models/tickts/tickts';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TicktsService {

  private apiUrl = environment.SERVIDOR;

  constructor(private http: HttpClient) {}

  listar(): Observable<any> {
    return this.http.get(`${this.apiUrl}/tickets/listar`);
  }

  criar(tickts: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/tickets/criar`, tickts);
  }

  atualizar(id: number, tickts: Tickts): Observable<any> {
    return this.http.put(`${this.apiUrl}/tickets/atualizar/${id}`, tickts);
  }

  deletar(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/tickets/deletar/${id}`);
  }

  iniciar(id: number, funcionarioResponsavel: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/tickets/iniciar/${funcionarioResponsavel}/${id}`, {});
  }

  reabrir(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/tickets/voltarAberto/${id}`, {});
  }

  finalizar(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/tickets/solucionar/${id}`, {});
  }

  cancelar(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/tickets/cancelar/${id}`, {});
  }

  listarPorStatus(status: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/tickets/listarPorStatus/${status}`);
  }

  listarPorUsuarioStatus(Usuario: string, status: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/tickets/buscarPorRaEStatus/${Usuario}/${status}`);
  }

  listarPorUsuario(Usuario: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/tickets/buscarPorRa/${Usuario}`);
  }
}

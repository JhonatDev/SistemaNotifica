import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tickts } from '../../models/tickts/tickts';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TicktsService {

  private apiUrl = environment.SERVIDOR;

  constructor(private http: HttpClient) {}

  // Método para obter o cabeçalho com o token
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Recupera o token (exemplo: localStorage)
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  listar(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/tickets/listar`, { headers });
  }

  criar(tickts: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/tickets/criar`, tickts, { headers });
  }

  atualizar(id: number, tickts: Tickts): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/tickets/atualizar/${id}`, tickts, { headers });
  }

  deletar(id: number): Observable<string> {
    const headers = this.getAuthHeaders();
    return this.http.delete<string>(`${this.apiUrl}/tickets/deletar/${id}`, { headers });
  }

  iniciar(id: number, funcionarioResponsavel: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/tickets/iniciar/${funcionarioResponsavel}/${id}`, {}, { headers });
  }

  reabrir(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/tickets/voltarAberto/${id}`, {}, { headers });
  }

  finalizar(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/tickets/solucionar/${id}`, {}, { headers });
  }

  cancelar(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/tickets/cancelar/${id}`, {}, { headers });
  }

  listarPorStatus(status: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/tickets/listarPorStatus/${status}`, { headers });
  }

  listarPorUsuarioStatus(Usuario: string, status: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/tickets/buscarPorRaEStatus/${Usuario}/${status}`, { headers });
  }

  listarPorUsuario(Usuario: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/tickets/buscarPorRa/${Usuario}`, { headers });
  }
}

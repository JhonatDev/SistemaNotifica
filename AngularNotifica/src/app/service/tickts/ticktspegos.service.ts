import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ticktspegos } from '../../models/tickts/ticktspegos';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TicktspegosService {

  private apiUrl = environment.SERVIDOR;

  constructor(private http: HttpClient) {}

  // Método para obter o cabeçalho com o token
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Recupera o token (exemplo: localStorage)
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  save(ticktspegos: Ticktspegos): Observable<Ticktspegos> {
    const headers = this.getAuthHeaders();
    return this.http.post<Ticktspegos>(`${this.apiUrl}/ticktspegos/save`, ticktspegos, { headers });
  }

  remove(nome : string): Observable<Ticktspegos> {
    const headers = this.getAuthHeaders();
    return this.http.delete<Ticktspegos>(`${this.apiUrl}/ticktspegos/removeByNomeUsuarioPego/${nome}`, { headers });
  }

  getById(id: number): Observable<Ticktspegos> {
    const headers = this.getAuthHeaders();
    return this.http.get<Ticktspegos>(`${this.apiUrl}/ticktspegos/findByIdTicket/${id}`, { headers });
  }

}

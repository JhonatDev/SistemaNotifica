import { HttpClient, HttpHeaders, HttpParams, HttpStatusCode } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NovoUsuarioService {


  constructor() { }

  http = inject(HttpClient)

  baseUrl = environment.SERVIDOR + "/novo-usuario"

  // Método para obter os cabeçalhos com o token
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Recupera o token JWT armazenado
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  // Método para criar um novo usuário
  createUser(usuario: string, password: string, isAdmin: boolean): Observable<any> {

    const headers = this.getAuthHeaders(); // Adiciona o cabeçalho Authorization
    const body = {
      usuario,
      password,
      isAdmin,
    };
    return this.http.post(`${this.baseUrl}/save`, body, { headers });
  }
}


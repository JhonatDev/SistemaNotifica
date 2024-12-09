import { HttpClient, HttpParams, HttpStatusCode } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NovoUsuarioService {


  constructor() { }

  http = inject(HttpClient)

  API = environment.SERVIDOR+"/novo-usuario"

  salvarNovoUsuario(usuario: string, password: string, isAdmin: boolean) {
    const params = new HttpParams()
      .set('usuario', usuario)
      .set('password', password)
      .set('isAdmin', isAdmin.toString()); // Convertendo booleano para string

    return this.http.post<HttpStatusCode.Created>(`${this.API}/save`, null, { params });
  }
}

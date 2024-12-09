import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubTipoProblemaService {

  private apiUrl = environment.SERVIDOR;

  constructor(private http: HttpClient) {}

  listarporsubtipo(tipoProblema: string): Observable<any> {
    // Obtenha o token da forma apropriada (exemplo: localStorage)
    const token = localStorage.getItem('token');

    // Crie os cabeçalhos HTTP com o token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // Faça a chamada HTTP com os cabeçalhos
    return this.http.get(`${this.apiUrl}/subtipoproblemas/listar/${tipoProblema}`, { headers });
  }
}

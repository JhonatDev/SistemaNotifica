import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubTipoProblemaService {

  private apiUrl = environment.SERVIDOR;

  constructor(private http: HttpClient) {}

  listarporsubtipo(tipoProblema: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/subtipoproblemas/listar/${tipoProblema}`);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubTipoProblemaService {

  constructor(private http: HttpClient) {}

  listarporsubtipo(tipoProblema: string): Observable<any> {
    return this.http.get(`http://localhost:8080/subtipoproblemas/listar/${tipoProblema}`);
  }

}

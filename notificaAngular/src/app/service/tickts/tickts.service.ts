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

  deletar(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/tickets/deletar/${id}`);
  }
}

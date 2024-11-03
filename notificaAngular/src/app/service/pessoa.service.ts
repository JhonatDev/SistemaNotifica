import { inject, Injectable } from '@angular/core';
import { Pessoa } from '../models/pessoa';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  constructor() { }

  http = inject(HttpClient);

  api = 'http://localhost:8080/pessoas';

  findAll() {
    return this.http.get<Pessoa[]>(this.api);
  }
}

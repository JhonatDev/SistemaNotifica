import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface LoginRequest {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/login';

  constructor(private http: HttpClient) {}

  login(loginData: LoginRequest): Observable<any> {
    alert('Fazendo login2...usuário: ' + loginData.username + ' senha: ' + loginData.password);
    return this.http.post<any>(this.apiUrl, loginData);
  }
}

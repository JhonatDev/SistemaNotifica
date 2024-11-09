import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { SharedService } from '../shared.service';


interface LoginRequest {
  username: string;
  password: string;
}
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/login';

  constructor(private http: HttpClient, private SharedService: SharedService) {}

  login(loginData: LoginRequest): Observable<any> {
    this.SharedService.ultimoLogin = loginData.username;
    return this.http.post<any>(this.apiUrl, loginData).pipe(
      tap(response => {
        this.SharedService.tipoUsuario = response.tipoUsuario;
      })
    );
  }
}

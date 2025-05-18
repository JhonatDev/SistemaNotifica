import { Component } from '@angular/core';
import { LoginService } from '../../service/login-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  login(): void {
    if (!this.username || !this.password) {
      alert('Preencha os campos de usuário e senha!');
      return;
    }

    this.loginService.login(this.username, this.password).subscribe({
      next: (response: any) => {
        const token = response.access_token;
        this.loginService.saveToken(token);
        const decodedToken = this.loginService.decodeToken();
        console.log('Usuário logado:', decodedToken);
        this.router.navigate(['/admin/principal']);
      },
      error: (error: any) => {
        console.error('Erro de autenticação:', error);
        alert('Usuário ou senha inválidos.');
      }
    });
  }
}

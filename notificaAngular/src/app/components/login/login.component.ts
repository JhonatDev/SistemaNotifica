import { Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../service/login/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  login = '';
  senha = '';

  router = inject(Router);

  constructor(private loginService: LoginService) {}

  onLogin() {
    alert('Fazendo login1...usuário: ' + this.login + ' senha: ' + this.senha);
    this.loginService.login({ username: this.login, password: this.senha })
      .subscribe({
        next: (response) => {
          console.log('Login bem-sucedido:', response);
          alert('Login bem-sucedido!');

          if (response.tipoUsuario === 'ADMIN') {
            this.router.navigate(['admin/pessoas']);
          } else {
            this.router.navigate(['pessoas']);
          }
        },
        error: (error) => {
          console.error('Erro no login:', error);
          alert('Erro no login: ' + error.message);

        }
      });
  }

  ontest1() {
    this.router.navigate(['admin/pessoas']);
  }

  ontest2() {
    this.router.navigate(['admin/principal']);
  }

  ontest3() {
    this.router.navigate(['admin/principal/criar']);
  }

  ontest4() {
    this.router.navigate(['admin/pessoas/edit/1']);
  }
}

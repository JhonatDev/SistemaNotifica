import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../service/login/login.service';
import { Router } from '@angular/router';
import { AdminlistComponent } from '../admin/adminlist/adminlist.component';
import { AdmindetalhesComponent } from '../admin/admindetalhes/admindetalhes.component';
import { SharedService } from '../../service/shared.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, AdminlistComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  login = '';
  senha = ''

  router = inject(Router);

  constructor(private loginService: LoginService, private sharedService: SharedService) {}

  ngOnInit(): void {
    document.documentElement.style.setProperty('--mdb-body-bg', '#00000000');//muda a cor do fundo
    document.documentElement.style.setProperty(
      'background',
       'url(https://svanegas-one.blackboard.com/bbcswebdav/xid-212801_1) no-repeat center center fixed'
    );//muda a imagem de fundo
    document.documentElement.style.setProperty('background-size', 'cover');//ajusta a imagem de fundo

  }

  onLogin() {
    this.loginService.login({ username: this.login, password: this.senha })
      .subscribe({
        next: (response) => {
          console.log('Login bem-sucedido:', response);

          if (response.tipoUsuario === 'ADMIN') {
            this.router.navigate(['admin/principal']);
          } else if (response.tipoUsuario === 'Usuarios') {
            this.router.navigate(['aluno/principal']);
          }
        },
        error: (error) => {
          console.error('Erro no login:', error);
          alert('Erro no login: ' + error.message);

        }
      });
  }

}



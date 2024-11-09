import { Component, inject} from '@angular/core';
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
  senha = '';

  router = inject(Router);

  constructor(private loginService: LoginService, private sharedService: SharedService) {}

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



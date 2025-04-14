import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import necessário para *ngIf
import { LoginService } from '../../../service/login-service.service';
import { log } from 'console';

@Component({
  selector: 'app-barramenu',
  standalone: true,
  templateUrl: './barramenu.component.html',
  styleUrls: ['./barramenu.component.scss'],
  imports: [CommonModule],
})
export class BarramenuComponent {
  tipoDeUsuario!: string;
  login!: string;
  exibirBuscaAvancada: boolean = false;
  listaLinks: Array<{ texto: string; href: string }> = [];
  showModal: boolean = false; // Variável para controlar o modal

  constructor(private loginService: LoginService) {
    this.tipoDeUsuario = this.loginService.jwtDecode()?.role || '';
    this.login = this.loginService.jwtDecode()?.username || '';

    // Define os links conforme o tipo de usuário
    switch (this.tipoDeUsuario) {
      case 'ROLE_admin':
        this.listaLinks = [
          { texto: 'CANCELADO', href: '/admin/principal/cancelados' },
          { texto: 'PENDENTES', href: '/admin/principal/pendentes' },
          { texto: 'EM ANDAMENTO', href: '/admin/principal/andamento' },
          { texto: 'CONCLUÍDOS', href: '/admin/principal/concluidos' },
        ];
        break;
      case 'ROLE_funcionario':
        this.listaLinks = [
          { texto: 'CANCELADO', href: '/funcionario/principal/cancelados' },
          { texto: 'PENDENTES', href: '/funcionario/principal/pendentes' },
          { texto: 'EM ANDAMENTO', href: '/funcionario/principal/andamento' },
          { texto: 'CONCLUÍDOS', href: '/funcionario/principal/concluidos' },
        ];
        break;
      case 'ROLE_user':
        this.listaLinks = [
          { texto: 'Principal', href: '/aluno/principal' },
          { texto: 'Tickets', href: '/aluno/ticket' },
          { texto: 'Configurações', href: '/aluno/configuracoes' },
        ];
        break;
      default:
        this.listaLinks = []; // Nenhum link se o tipo de usuário não for reconhecido
    }
    
  }

  irParaPrincipal(): void {
    // Lógica para redirecionar o usuário admin, funcionario ou aluno para a rota principal
    let rotaPrincipal: string;
    switch (this.tipoDeUsuario) {
      case 'ROLE_admin':
        rotaPrincipal = '/admin/principal';
        break;
      case 'ROLE_funcionario':
        rotaPrincipal = '/funcionario/principal';
        break;
      case 'ROLE_user':
        rotaPrincipal = '/aluno/principal';
        break;
      default:
        rotaPrincipal = '/login'; // Redireciona para o login se o tipo de usuário não for reconhecido
        break;
    }
    window.location.href = rotaPrincipal;
  }

  toggleBuscaAvancada(): void {
    this.exibirBuscaAvancada = !this.exibirBuscaAvancada;
  }

  // Abre o modal de confirmação
  openLogoutModal(): void {
    this.showModal = true;
  }

  // Fecha o modal
  closeModal(): void {
    this.showModal = false;
  }

  // Confirma a saída e redireciona para o login
  confirmLogout(): void {
    window.location.href = '/login';
  }
}

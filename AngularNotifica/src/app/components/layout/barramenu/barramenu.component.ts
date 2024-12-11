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
  exibirBuscaAvancada: boolean = false;
  listaLinks: Array<{ texto: string; href: string }> = [];
  showModal: boolean = false; // Variável para controlar o modal

  constructor(private loginService: LoginService) {
    this.tipoDeUsuario = this.loginService.jwtDecode()?.role || '';

    // Define os links conforme o tipo de usuário
    this.listaLinks = this.tipoDeUsuario === 'ROLE_admin'
      ? [
          { texto: 'CANCELADO', href: '/admin/principal/cancelados' },
          { texto: 'PENDENTES', href: '/admin/principal/pendentes' },
          { texto: 'EM ANDAMENTO', href: '/admin/principal/andamento' },
          { texto: 'CONCLUÍDOS', href: '/admin/principal/concluidos' },
        ]
      : [
          { texto: 'PENDENTES', href: '/aluno/principal/pendentes' },
          { texto: 'EM-ANDAMENTO', href: '/aluno/principal/andamento' },
          { texto: 'CONCLUÍDOS', href: '/aluno/principal/concluidos' },
        ];
  }

  irParaPrincipal(): void {
    // Lógica para redirecionar o usuário
    const rotaPrincipal = this.tipoDeUsuario === 'ROLE_admin'
      ? '/admin/principal'
      : '/aluno/principal';
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

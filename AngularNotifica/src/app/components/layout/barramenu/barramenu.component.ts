import { Component } from '@angular/core';
import { SharedService } from '../../../service/shared.service';
import { CommonModule } from '@angular/common'; // Import necessário para *ngIf

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

  constructor(private sharedService: SharedService) {
    this.tipoDeUsuario = this.sharedService.tipoUsuario;

    // Define os links conforme o tipo de usuário
    this.listaLinks = this.tipoDeUsuario === 'ADMIN'
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
    const rotaPrincipal = this.tipoDeUsuario === 'ADMIN' 
      ? '/admin/principal' 
      : '/aluno/principal';
    window.location.href = rotaPrincipal;
  }

  toggleBuscaAvancada(): void {
    this.exibirBuscaAvancada = !this.exibirBuscaAvancada;
  }
}

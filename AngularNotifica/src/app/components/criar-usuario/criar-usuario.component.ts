import { Component, inject, ViewChild, TemplateRef, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { NovoUsuarioService } from '../../service/novo-usuario.service';

@Component({
  selector: 'app-criar-usuario',
  standalone: true,
  imports: [
    CommonModule,
    MdbModalModule,
    FormsModule
  ],
  templateUrl: './criar-usuario.component.html',
  styleUrls: ['./criar-usuario.component.scss']
})
export class CriarUsuarioComponent {

  @ViewChild('modalCriarUsuario') modalCriarUsuario!: TemplateRef<any>;
  @Output() retorno = new EventEmitter<void>();
  usuario: string = '';
  senha: string = '';
  tipoUsuario: boolean = false;  // Utilizando booleano para controlar o checkbox
  showModal: boolean = false; // Variável para controlar o modal
  alert!: string;

  constructor(private usuariosService: NovoUsuarioService, private router: Router) {}

  onCreateUser() {
    if (this.usuario && this.senha && this.tipoUsuario !== undefined) {
      const novoUsuario = {
        username: this.usuario,
        password: this.senha,
        role: this.tipoUsuario ? 'admin' : 'user'  // Definindo 'admin' se o checkbox estiver marcado
      };

      this.usuariosService.createUser(novoUsuario.username, novoUsuario.password, novoUsuario.role === 'admin').subscribe({
        next: (response: any) => {
          console.log('Usuário criado com sucesso!', response);
          this.alert = 'Usuário criado com sucesso!';
          this.showModal = true;
        },
        error: (error) => {
          console.error('Erro ao criar usuário:', error);
          this.alert = 'Usuário já existe!';
          this.showModal = true;
        }
      });
    } else {
      this.alert = 'Preencha todos os campos!';
      this.showModal = true;
    }
  }

  // Fecha o modal
  closeModal(): void {
    this.showModal = false;
  }
}


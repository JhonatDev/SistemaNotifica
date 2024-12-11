import { Component, ViewEncapsulation } from '@angular/core';  // Importe ViewEncapsulation
import { FormsModule } from '@angular/forms';  // Importe o FormsModule
import { CommonModule } from '@angular/common';  // Importe o CommonModule

@Component({
  selector: 'app-criar-usuario',
  standalone: true,  // Componente standalone
  imports: [FormsModule, CommonModule],  // Adicione o CommonModule aqui
  templateUrl: './criar-usuario.component.html',
  styleUrls: ['./criar-usuario.component.css'],
  encapsulation: ViewEncapsulation.None  // Certifique-se de definir a encapsulação corretamente
})
export class CriarUsuarioComponent {
  usuario: string = '';
  senha: string = '';
  tipoUsuario: string = '';
  formVisible: boolean = true;  // Variável para controlar a visibilidade do formulário

  onCreateUser() {
    // Aqui você pode fazer alguma validação ou enviar os dados para uma API
    console.log('Usuário:', this.usuario);
    console.log('Senha:', this.senha);
    console.log('Tipo de Usuário:', this.tipoUsuario);
  }

  closeForm() {
    this.formVisible = false;  
  }
}

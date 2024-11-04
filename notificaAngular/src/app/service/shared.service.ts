import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private storageKeyLogin = 'ultimoLogin';
  private storageKeyTipoUsuario = 'tipoUsuario';

  // Carregar o último login do LocalStorage ou usar um valor padrão
  get ultimoLogin(): string {
    return localStorage.getItem(this.storageKeyLogin) || 'Valor padrão de login';
  }

  set ultimoLogin(novoLogin: string) {
    localStorage.setItem(this.storageKeyLogin, novoLogin);
  }

  // Carregar o tipo de usuário do LocalStorage ou usar um valor padrão
  get tipoUsuario(): string {
    return localStorage.getItem(this.storageKeyTipoUsuario) || 'Tipo padrão de usuário';
  }

  set tipoUsuario(novoTipo: string) {
    localStorage.setItem(this.storageKeyTipoUsuario, novoTipo);
  }
}

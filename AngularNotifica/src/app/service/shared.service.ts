import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private readonly storageKeyLogin = 'ultimoLogin';
  private readonly storageKeyTipoUsuario = 'tipoUsuario';
  private readonly storageKeyToken = 'token';

  // Verifica se o localStorage está disponível
  private isLocalStorageAvailable(): boolean {
    try {
      const testKey = '__test__';
      localStorage.setItem(testKey, testKey);
      localStorage.removeItem(testKey);
      return true;
    } catch {
      return false;
    }
  }

  // Carregar o último login do LocalStorage ou usar um valor padrão
  get ultimoLogin(): string {
    if (this.isLocalStorageAvailable()) {
      return localStorage.getItem(this.storageKeyLogin) ?? 'Valor padrão de login';
    }
    return 'Valor padrão de login';
  }

  set ultimoLogin(novoLogin: string) {
    if (this.isLocalStorageAvailable()) {
      localStorage.setItem(this.storageKeyLogin, novoLogin);
    }
  }

  // Carregar o tipo de usuário do LocalStorage ou usar um valor padrão
  get tipoUsuario(): string {
    if (this.isLocalStorageAvailable()) {
      return localStorage.getItem(this.storageKeyTipoUsuario) ?? 'Tipo padrão de usuário';
    }
    return 'Tipo padrão de usuário';
  }

  set tipoUsuario(novoTipo: string) {
    if (this.isLocalStorageAvailable()) {
      localStorage.setItem(this.storageKeyTipoUsuario, novoTipo);
    }
  }

  // Carregar o token do LocalStorage ou usar um valor padrão
  get token(): string {
    if (this.isLocalStorageAvailable()) {
      return localStorage.getItem(this.storageKeyToken) ?? 'Token padrão';
    }
    return 'Token padrão';
  }

  set token(novoToken: string) {
    if (this.isLocalStorageAvailable()) {
      localStorage.setItem(this.storageKeyToken, novoToken);
    }
  }

}

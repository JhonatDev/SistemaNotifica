import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../service/login-service.service';

export const authRouteGuardGuard: CanActivateFn = (route, state) => {
  let roleUser = inject(LoginService)
  let rota = inject(Router)

  if(roleUser.hasPermission('ROLE_user') && state.url=="/admin/principal"){
    rota.navigate(["/aluno/principal"])
    return false;
  }

  if(roleUser.hasPermission('ROLE_admin') && state.url=="/aluno/principal"){
    rota.navigate(["/admin/principal"])
    return false;
  }





  return true;
};

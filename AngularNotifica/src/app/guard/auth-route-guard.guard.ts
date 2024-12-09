import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../service/login-service.service';

export const authRouteGuardGuard: CanActivateFn = (route, state) => {
  let roleUser = inject(LoginService)
  let rota = inject(Router)

  if(roleUser.hasPermission('user') && state.url=="/admin/pessoas/insert/usuario"){
    rota.navigate(['/admin/pessoas']);
    return false;
  }
  return true;
};

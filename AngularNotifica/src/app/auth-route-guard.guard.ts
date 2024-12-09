import { CanActivateFn } from '@angular/router';

export const authRouteGuardGuard: CanActivateFn = (route, state) => {
  return true;
};

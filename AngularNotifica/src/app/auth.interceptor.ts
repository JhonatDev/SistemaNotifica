import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './service/login-service.service';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  const token = loginService.getToken();

  if (token) {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(request).pipe(
    catchError((error) => {
      if (error.status === 401 || error.status === 403) {
        loginService.logout();
      }
      return throwError(() => error);
    })
  );
};

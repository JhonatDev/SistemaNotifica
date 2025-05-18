import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const meuhttpInterceptor: HttpInterceptorFn = (request, next) => {
  const router = inject(Router);

  const token = localStorage.getItem('token');
  // Não adicionar token se estiver na página de login
  if (token && !router.url.includes('/login')) {
    request = request.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
  }

  return next(request).pipe(
    catchError((err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          alert('Sessão expirada ou não autorizado. Faça login novamente.');
          router.navigate(['/login']);
        } else if (err.status === 403) {
          alert('Acesso negado. Você não tem permissão para acessar este recurso.');
          router.navigate(['/login']);
        } else {
          console.error('Erro HTTP:', err);
        }
      } else {
        console.error('Erro inesperado:', err);
      }

      return throwError(() => err);
    })
  );
};

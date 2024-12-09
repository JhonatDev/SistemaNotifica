import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ImageUploadService {
  private baseUrl = `${environment.SERVIDOR}/image`;

  constructor(private http: HttpClient) {}

  // Método para obter os cabeçalhos com o token
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Recupera o token JWT armazenado
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  // Método para fazer o upload da imagem
  uploadImage(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('image', file);

    const headers = this.getAuthHeaders(); // Adiciona o cabeçalho Authorization
    return this.http.post(`${this.baseUrl}/upload`, formData, {
      headers,
      responseType: 'text',
    });
  }

  // Método para deletar a imagem
  deleteImage(fileName: string): Observable<any> {
    const headers = this.getAuthHeaders(); // Adiciona o cabeçalho Authorization
    return this.http.delete(`${this.baseUrl}/delete/${fileName}`, {
      headers,
      responseType: 'text',
    });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ImageUploadService {
  private baseUrl = `${environment.SERVIDOR}/image`;

  constructor(private http: HttpClient) {}

  // Método para fazer o upload da imagem
  uploadImage(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('image', file);
    return this.http.post(`${this.baseUrl}/upload`, formData, { responseType: 'text' });
  }

  // Método para deletar a imagem
  deleteImage(fileName: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${fileName}`, { responseType: 'text' });
  }
}

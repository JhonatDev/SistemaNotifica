import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ImageUploadService {
  private baseUrl = 'http://localhost:8080/image';

  constructor(private http: HttpClient) {}

  // Método para fazer o upload da imagem
  uploadImage(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('image', file);
    return this.http.post(`${this.baseUrl}/upload`, formData, { responseType: 'text' });
  }
}

import { Image } from './../model/image';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private baseURL = "http://localhost/products/image";
  constructor(private httpClient: HttpClient) {}


  getImageById(IDProduct: number): Observable<Image[]> {
    return this.httpClient.get<Image[]>(`${this.baseURL}?productID=${IDProduct}`);
  }

  saveImage(imageParam: Image, productID: number): Observable<Image> {
    const image = {
      id: imageParam.id,
      productID: productID,
      content: imageParam.content,
    }
    return this.httpClient.post<Image>(`${this.baseURL}`, image);
  }

  deleteImage(productID: number): Observable<Object> {
    return this.httpClient.delete<Object>(`${this.baseURL}?imageID=${productID}`);
  }

}

import { ProductComplete } from './../model/productComplete';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseURL = "http://localhost/products";
  constructor(private httpClient: HttpClient) {}



  getProductList(type: string): Observable<ProductComplete[]> {
    return this.httpClient.get<ProductComplete[]>(`${this.baseURL}/get?type=${type}`);
  }

  createProduct(product: ProductComplete): Observable<ProductComplete> {
    const productBase = {
      name: product.name,
      productType: product.productType,
    }
    // JSON.stringify(product)
    return this.httpClient.post<ProductComplete>(`${this.baseURL}/save`, productBase)
  }


  getProductById(id: number): Observable<ProductComplete> {
    return this.httpClient.get<ProductComplete>(`${this.baseURL}/get/${id}`);
  }

  updateProduct(productComplete: ProductComplete): Observable<Object> {
    const product = {
      id: productComplete.id,
      name: productComplete.name,
      productType: productComplete.productType,
    }

    return this.httpClient.put(`${this.baseURL}/update`, product);
  }

  deletePoduct(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/delete/${id}`);
  }

  getTypes(): Observable<String[]> {
    return this.httpClient.get<String[]>(`${this.baseURL}/types`);
  }
}

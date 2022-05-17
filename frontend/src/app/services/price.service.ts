import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, LOCALE_ID } from '@angular/core';
import { Price } from '../model/price';

@Injectable({
  providedIn: 'root'
})
export class PriceService {

  private baseURL = "http://localhost/prices";
  constructor(private httpClient: HttpClient) {}

  // new Date().toISOString();
  private getDateOneYearLater(date: Date): Date{
    var year = date.getFullYear();
    var month = date.getMonth();
    var day = date.getDate();
    return new Date(year + 1, month, day);
  }

  getPriceByID(id: number): Observable<Price> {
    return this.httpClient.get<Price>(`${this.baseURL}/?productID=${id}`);
  }


  savePrice(priceParam: number, IDProduct: number, isDiscountParam: boolean): Observable<Price>{
    const date = new Date();
    const priceObj = {
      productID: IDProduct,
      price: priceParam,
      start: date,
      end: this.getDateOneYearLater(date),
      isDiscount: isDiscountParam
    }

    return this.httpClient.post<Price>(`${this.baseURL}/`, priceObj);
  }

  deletePrice(price: Price): Observable<Object> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: price,
    };

    return this.httpClient.delete<Object>(`${this.baseURL}/`, httpOptions);
  }

  deletePriceByProductID(id: number): Observable<Object> {
    return this.httpClient.delete<Object>(`${this.baseURL}/product/${id}`);
  }



}


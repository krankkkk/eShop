import { Info } from './../model/info';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InfoService {


  private baseURL = "http://localhost/products/info";
  constructor(private httpClient: HttpClient) {}


  getInfoById(IDProduct: number): Observable<Info> {
    return this.httpClient.get<Info>(`${this.baseURL}?productID=${IDProduct}`);
  }

  saveInfo(infoParam: Info, productID: number): Observable<Info> {
    const info = {
      id: infoParam.id,
      productID: productID,
      techDoc: infoParam.techDoc,
      additionalInformation: infoParam.additionalInformation,
    }
    return this.httpClient.post<Info>(`${this.baseURL}`, info);
  }

  deleteInfo(productID: number): Observable<Object> {
    return this.httpClient.delete<Object>(`${this.baseURL}?infoID=${productID}`);
  }

}

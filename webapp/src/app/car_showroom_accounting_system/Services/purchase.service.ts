import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

constructor(private http: HttpClient) { }

getPurchaseList(parameter: any)
{
  return this.http.get<any>(Common.baseUrl + '/purchaseRecords?' + parameter);
}

addPurchase(data: any)
{
  return this.http.post<any>(localStorage.getItem('link'), data);
}
deletePurchase(link: any)
{
  return this.http.delete<any>(link);
}
public getPurchaseById()
{
  return this.http.get<any>(localStorage.getItem('link'));
}
public updatePurchase(data: any)
{
  return this.http.put<any>(localStorage.getItem('link'), data);
}

}

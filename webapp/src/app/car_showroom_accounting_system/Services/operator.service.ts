import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class OperatorService {

  constructor(private http: HttpClient) { }

  public addOperator(data: any)
  {
    return this.http.post<any>(Common.baseUrl + '/operators', data);
  }
  public updateOperator(data: any)
  {
    return this.http.put<any>(localStorage.getItem('link'), data);
  }
  public getOperatorList()
  {
    return this.http.get<any>(Common.baseUrl + '/operators');
  }
  public deleteOperator(link: any)
  {
    return this.http.delete<any>(link);
  }
  public getOperatorById()
  {
    return this.http.get<any>(localStorage.getItem('link'));
  }
  public resetPassword(data: any)
  {
    return this.http.patch<any>(localStorage.getItem('link'), data);
  }
  public changePassword(data: any)
  {
    return this.http.patch<any>(localStorage.getItem('link'), data);
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {

  constructor(private http: HttpClient) { }

  public forgotPassword(data: any)
  {
    const reqHeader = new HttpHeaders({'No-Auth': 'True'});
    return this.http.post<any>(Common.url + '/forgotPassword', data, {headers: reqHeader});
  }
  public confirmResetPassword(data: any, token: any)
  {
    const reqHeader = new HttpHeaders({'No-Auth': 'True', token});
    // reqHeader.set('token', token);
    return this.http.patch<any>(Common.url + '/confirmResetPassword', data, {headers: reqHeader});
  }
}

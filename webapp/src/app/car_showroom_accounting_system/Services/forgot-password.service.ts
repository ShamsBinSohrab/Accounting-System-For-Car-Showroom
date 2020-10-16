import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Common } from '../Common/common';
import * as jwt_decode from 'jwt-decode';

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
    const decodedPasswordToken = jwt_decode(token);
    // console.log(decodedPasswordToken.sub);
    const reqHeader = new HttpHeaders({'No-Auth': 'True', 'x-password-reset-token': decodedPasswordToken.sub});
    // reqHeader.set('token', token);
    return this.http.patch<any>(Common.url + '/confirmResetPassword', data, {headers: reqHeader});
  }
}

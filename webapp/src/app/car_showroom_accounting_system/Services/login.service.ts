import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import {Common} from '../Common/common';
import * as jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(
    private http: HttpClient,
    private router: Router
    ) { }

  public authenticate(loginData: any)
  {
    const reqHeader = new HttpHeaders({'No-Auth': 'True'});
    return this.http.post<any>(Common.url + '/authenticate', loginData, {headers: reqHeader})
    .pipe(map(user => {
      if (user && user.authToken) {
          // Decode Auth Token
          const decodedAuthToken = jwt_decode(user.authToken);
          // store user details in local storage to keep user logged in
          localStorage.setItem('auth_token', user.authToken);
          localStorage.setItem('company_token', user.companyToken);
          localStorage.setItem('user_role', decodedAuthToken.AUTHORITIES);
          localStorage.setItem('username', decodedAuthToken.sub);
          // localStorage.setItem('username', user.username);
      }
  }));
  }

  public Get()
  {
    return this.http.get<any>(Common.baseUrl + '/WeatherForecast');
  }

  public logout() {
    // remove user data from local storage for log out
    localStorage.removeItem('auth_token');
    localStorage.removeItem('company_token');
    localStorage.removeItem('user_role');
    localStorage.removeItem('username');
    localStorage.clear();
    this.router.navigate(['/login']);
}
}

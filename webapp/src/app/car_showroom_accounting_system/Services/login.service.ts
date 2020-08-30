import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import {Common} from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  constructor(private http: HttpClient) { }

  public authenticate(loginData: any)
  {
    const reqHeader = new HttpHeaders({'No-Auth': 'True'});
    return this.http.post<any>(Common.url + '/authenticate', loginData, {headers: reqHeader})
    .pipe(map(user => {
      if (user && user.jwtToken) {
          console.log(user);
          // store user details in local storage to keep user logged in
          localStorage.setItem('token', user.jwtToken);
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
    localStorage.removeItem('token');
    localStorage.removeItem('username');
}
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { }

  public addCompany(data: any)
  {
    // const reqHeader = new HttpHeaders({'No-Company-Token': 'True'});, {headers: reqHeader}
    return this.http.post<any>(Common.suBaseUrl + '/companies', data);
  }
  public getCompanyToken(data: any)
  {
    // const reqHeader = new HttpHeaders({'No-Company-Token': 'True'});, {headers: reqHeader}
    return this.http.get<any>(Common.suBaseUrl + '/companies/' + data + '/token')
    .pipe(map(company => {
      if (company && company.tenantAccessorToken) {
          console.log(company);
          // store company token details in local storage
          localStorage.setItem('company-token', company.tenantAccessorToken);
      }
  }));
  }
}

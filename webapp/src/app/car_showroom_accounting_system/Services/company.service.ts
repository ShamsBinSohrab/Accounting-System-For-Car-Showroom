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
    return this.http.post<any>(Common.baseUrl + '/companies', data);
  }
  public updateCompany(id: any, data: any)
  {
    // const reqHeader = new HttpHeaders({'No-Company-Token': 'True'});, {headers: reqHeader}
    return this.http.put<any>(Common.baseUrl + '/companies/' + id, data);
  }

  getCompanyList(parameter: string) {
    return this.http.get<any>(Common.baseUrl + '/companies?' + parameter);
  }

  getCompanybyId(id: string) {
    return this.http.get<any>(Common.baseUrl + '/companies/' + id);
  }

  public getCompanyToken(data: any)
  {
    // const reqHeader = new HttpHeaders({'No-Company-Token': 'True'});, {headers: reqHeader}
    return this.http.get<any>(Common.baseUrl + '/companies/' + data + '/token')
    .pipe(map(company => {
      if (company && company.companyToken) {
          console.log(company);
          // store company token details in local storage
          localStorage.setItem('company_token', company.companyToken);
      }
  }));
  }
}

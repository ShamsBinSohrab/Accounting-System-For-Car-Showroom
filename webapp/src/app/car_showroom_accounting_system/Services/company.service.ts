import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { }

  public addCompany(data: any)
  {
    return this.http.post<any>(Common.suBaseUrl + '/companies', data);
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class SellService {
  constructor(private http: HttpClient) { }

  getSellList(parameter: any)
  {
    return this.http.get<any>(Common.baseUrl + '/sellRecords?' + parameter);
  }

  addSell(data: any)
  {
    return this.http.post<any>(localStorage.getItem('link'), data);
  }
  deleteSell(link: any)
  {
    return this.http.delete<any>(link);
  }
  public getSellById()
  {
    return this.http.get<any>(localStorage.getItem('link'));
  }
  public updateSell(data: any)
  {
    return this.http.put<any>(localStorage.getItem('link'), data);
  }

}

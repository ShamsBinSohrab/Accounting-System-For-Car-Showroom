import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  constructor(private http: HttpClient) { }

  getExpenseList(parameter: any)
  {
    return this.http.get<any>(Common.baseUrl + '/expenseRecords?' + parameter);
  }

  addExpense(data: any)
  {
    return this.http.post<any>(localStorage.getItem('link'), data);
  }
  deleteExpense(link: any)
  {
    return this.http.delete<any>(link);
  }
  public getExpenseById()
  {
    return this.http.get<any>(localStorage.getItem('link'));
  }
  public updateExpense(data: any)
  {
    return this.http.put<any>(localStorage.getItem('link'), data);
  }

}

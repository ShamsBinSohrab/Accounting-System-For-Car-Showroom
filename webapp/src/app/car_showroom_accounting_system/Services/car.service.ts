import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Common } from '../Common/common';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient) { }

  public addCar(data: any)
  {
    return this.http.post<any>(Common.baseUrl + '/cars', data);
  }
  public getCar()
  {
    return this.http.get<any>(Common.baseUrl + '/cars');
  }
  public deleteCar(data: any)
  {
    console.log(data);
    return this.http.delete<any>(Common.baseUrl + '/deletecCars', data);
  }
}

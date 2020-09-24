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
  public updateCar(data: any)
  {
    console.log(data);
    return this.http.put<any>(localStorage.getItem('link'), data);
  }
  public getCar()
  {
    return this.http.get<any>(Common.baseUrl + '/cars');
  }
  public deleteCar(link: any)
  {
    return this.http.delete<any>(link);
  }
  public getCarById()
  {
    return this.http.get<any>(localStorage.getItem('link'));
  }

  public getCarMakeList()
  {
    // tslint:disable-next-line:max-line-length
    // const make: Array<string> = ['TOYOTA', 'NISSAN', 'HONDA', 'BMW', 'AUDI', 'LAND_ROVER', 'MERCEDES', 'VOLVO', 'FORD', 'JAGUAR', 'MITSUBISHI', 'SUBARU', 'TESLA', 'VOLKSWAGEN'];

    const make = '';

    return make;
  }
}

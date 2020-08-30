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
  public updateCar(carId: any, data: any)
  {
    return this.http.put<any>(Common.baseUrl + '/cars/' + carId, data);
  }
  public getCar()
  {
    return this.http.get<any>(Common.baseUrl + '/cars');
  }
  public deleteCar(carId: any)
  {
    return this.http.delete<any>(Common.baseUrl + '/cars/' + carId);
  }
  public getCarById(carId: any)
  {
    return this.http.get<any>(Common.baseUrl + '/cars/' + carId);
  }

  public getCarMakeList()
  {
    // const make: Array<string> = ['TOYOTA', 'NISSAN', 'HONDA', 'BMW', 'AUDI', 'LAND_ROVER', 'MERCEDES', 'VOLVO', 'FORD', 'JAGUAR', 'MITSUBISHI', 'SUBARU', 'TESLA', 'VOLKSWAGEN'];

    const make = '';

    return make;
  }
}

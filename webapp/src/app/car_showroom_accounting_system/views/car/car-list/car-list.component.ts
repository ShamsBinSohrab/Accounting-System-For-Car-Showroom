import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { LoginService } from 'src/app/car_showroom_accounting_system/Services/login.service';
import { EncryptionDescryptionService } from 'src/app/car_showroom_accounting_system/Services/encryption-descryption.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CarService } from 'src/app/car_showroom_accounting_system/Services/car.service';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {
  data: Array<any>;
  constructor(
                private formBuilder: FormBuilder,
                private carService: CarService,
                private router: Router,
                private toastrService: ToastrService
  ) {
    if (localStorage.getItem('company_token') === '')
    {
      this.router.navigate(['/company/list']);
    }
  }

  ngOnInit(): void {
    this.data = new Array();
    this.getCar();
  }

  getCar()
  {
    this.carService.getCar()
        .subscribe(
          data => {
            this.data = data;
          },
          error => {
            this.toastrService.error(error.error, 'Error!');
          }
        );
  }

  deleteCar(link: string)
  {
    this.carService.deleteCar(link)
    .subscribe(
      data => {
        this.toastrService.success('Delete Successful', 'Success');
        this.ngOnInit();
      },
      error => {
        this.toastrService.error(error.error);
      }
    );
  }

  carDetails(link: string)
  {
    localStorage.setItem('link', link);
    // this.router.navigate(['/car/details', { carId }]);
    this.router.navigate(['/car/details']);
  }
  updateCar(link: string)
  {
    localStorage.setItem('link', link);
    this.router.navigate(['/car/update']);
    // this.router.navigate(['/car/update', { carId }]);
  }
  addCar()
  {
    this.router.navigate(['/car/create']);
  }
  carAction(link: any, status: any)
  {
    localStorage.setItem('link', link);
    if (status === 'purchaseRecord')
    {
      this.router.navigate(['/purchase/create']);
    }
    else
    {
      this.router.navigate(['/sell/create']);
    }
  }

}

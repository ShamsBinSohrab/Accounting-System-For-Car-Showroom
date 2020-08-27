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
  ) { }

  ngOnInit(): void {
    this.data = new Array();
    this.carService.getCar()
        .subscribe(
          data => {
            this.data = data;
            console.log(this.data);
          },
          error => {
            this.toastrService.error(error.statusText);
            console.log(error);
          }
        );
  }

}

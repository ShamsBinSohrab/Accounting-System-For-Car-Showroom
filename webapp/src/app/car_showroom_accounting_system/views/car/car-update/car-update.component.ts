import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarService } from 'src/app/car_showroom_accounting_system/Services/car.service';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-car-update',
  templateUrl: './car-update.component.html',
  styleUrls: ['./car-update.component.scss']
})
export class CarUpdateComponent implements OnInit {

  id: any;
  data: any;
  updateCar: FormGroup;
  makeList: string[];
  typeList: string[];

  constructor(
    private activeRoute: ActivatedRoute,
    private carService: CarService,
    private route: Router,
    private toastrService: ToastrService,
    private formBiulder: FormBuilder,
    private location: Location
    ) { }

  ngOnInit() {
    this.id = this.activeRoute.snapshot.paramMap.get('carId');
    this.loadForm();
    this.getMake();
    this.getType();
    this.getDetails(this.id);
  }

  loadForm()
  {
    this.updateCar = this.formBiulder.group({
      chassisNo: ['', [Validators.required]],
      details: this.formBiulder.group(
        {
          make: ['', [Validators.required]],
          name: ['', [Validators.required]],
          type: ['', [Validators.required]],
          modelYear: ['', [Validators.required]],
          color: ['', [Validators.required]],
        }
      ),
    });
  }

  getDetails(carId: any)
  {
    this.carService.getCarById(carId)
                  .subscribe(
                    data => {
                      console.log(data);

                      this.updateCar.patchValue({
                        chassisNo: data.chassisNo,
                        details:
                          {
                            make: data.details.make,
                            name: data.details.name,
                            type: data.details.type,
                            modelYear: data.details.modelYear,
                            color: data.details.color,
                          }
                      });
                    },
                    error => {
                      console.log(error.error);
                      this.toastrService.error(error.error);
                    }
                    );
  }


  getMake()
  {
    this.makeList = [
      'TOYOTA', 'NISSAN', 'HONDA', 'BMW', 'AUDI', 'LAND_ROVER', 'MERCEDES', 'VOLVO', 'FORD', 'JAGUAR', 'MITSUBISHI', 'SUBARU', 'TESLA', 'VOLKSWAGEN'
    ];
  }
  getType()
  {
    this.typeList = [
      'CONVERTIBLE', 'COUPE', 'HATCHBACK', 'JEEP', 'MINIVAN', 'PICKUP_TRUCK', 'SEDAN', 'SPORTS_CAR', 'STATION_WAGON', 'SUV'
    ];
  }

  onSubmit()
  {
    if (this.updateCar.invalid)
    {
      this.toastrService.warning('Please Fill All Field!', 'Warning!');
      return;
    }
    this.carService.updateCar(this.id, this.updateCar.value)
                  .subscribe(
                    data => {
                      this.toastrService.success('Update Successful', 'Success');
                      this.ngOnInit();
                      this.location.back();
                    },
                    error => {
                      console.log(error.error);
                      this.toastrService.error(error.error);
                    }
                    );
  }

  back()
  {
    this.location.back();
  }
}

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
  optionsForDropDown: { value: string; display: string; }[];
  firstStep = true;
  secondStep = false;
  option: string[] = [];

  constructor(
    private activeRoute: ActivatedRoute,
    private carService: CarService,
    private route: Router,
    private toastrService: ToastrService,
    private formBiulder: FormBuilder,
    private location: Location
    ) { }

  ngOnInit() {
    // this.id = this.activeRoute.snapshot.paramMap.get('carId');
    this.getMake();
    this.getType();
    this.getOptions();
    this.loadForm();
    this.getDetails();
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
          engineNo: ['', Validators.required],
          mileage: ['', Validators.required],
          cc: ['', Validators.required],
          transmission: [''],
          fuelType: [''],
          // option: [[]],
          options: []
        }
      ),
    });
  }

  getDetails()
  {
    this.carService.getCarById()
                  .subscribe(
                    data => {
                      // this.option = data.details.options;
                      this.updateCar.patchValue({
                        chassisNo: data.chassisNo,
                        details:
                          {
                            make: data.details.make,
                            name: data.details.name,
                            type: data.details.type,
                            modelYear: data.details.modelYear,
                            color: data.details.color,
                            engineNo: data.details.engineNo,
                            mileage: data.details.mileage,
                            cc: data.details.cc,
                            transmission: data.details.transmission,
                            fuelType: data.details.fuelType,
                            // option: data.details.options,
                            options: data.details.options
                          }
                      });
                    },
                    error => {
                      this.toastrService.error(error.error);
                    }
                    );
  }


  firstStepComplete()
  {
    this.firstStep = false;
    this.secondStep = true;
  }

  firstStepBack()
  {
    this.firstStep = true;
    this.secondStep = false;
  }

  getOptions()
  {
    // tslint:disable-next-line:max-line-length
    this.optionsForDropDown = [{value: 'WOODEN_PANEL', display: 'Wooden Panel'}, {value: 'CRUISE_CONTROL', display: 'Cruise Control'}, {value: 'HIGH_FLOOR', display: 'High Floor'}, {value: 'RAIN_SHED', display: 'Rain Shed'}, {value: 'DVD_PLAYER', display: 'DVD Player'}, {value: 'BACK_CAMERA', display: 'Back Camera'}, {value: 'FOUR_CAMERAS', display: 'Four Cameras'}, {value: 'SUNROOF', display: 'Sun Roof'}, {value: 'WINKER_MIRROR', display: 'Winker Mirror'}, {value: 'AC', display: 'AC'}, {value: 'REAR_AC', display: 'Rear AC'}, {value: 'THIRD_ROW_SEATS', display: 'Third Row Seat'}, {value: 'AIR_BAG', display: 'Air Bag'}, {value: 'HIGH_ROOF', display: 'High Roof'}, {value: 'LOW_ROOF', display: 'Low Roof'}, {value: 'WOODEN_STEERING', display: 'Wooden Steering'}, {value: 'LOW_FLOOR', display: 'Low Floor'}, {value: 'ROOF_RAIL', display: 'Roof Rail'}];
  }

  // onAdd($event)
  // {
  //   this.option.push($event.value);
  // }

  // onRemove($event)
  // {
  //   for (let index = 0; index < this.option.length; index++) {
  //     if (this.option[index] === $event.value)
  //     {
  //       this.option[index] = null;
  //     }
  //   }
  //   // this.option.reduce($event.value);
  // }

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
    this.carService.updateCar(this.updateCar.value)
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

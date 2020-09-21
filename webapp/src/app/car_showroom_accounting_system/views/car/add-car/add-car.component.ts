import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CarService } from 'src/app/car_showroom_accounting_system/Services/car.service';
import { Router } from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent implements OnInit {
  CarForm: FormGroup;
  makeList: string[];
  typeList: string[];
  optionsForDropDown: { value: string; display: string; }[];
  // options: string[];
  firstStep = true;
  secondStep = false;
  selectedtag: string[];
  option: string[] = [];
  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private carService: CarService,
    private route: Router,
    private location: Location,
    ) { }

  ngOnInit(): void {
    this.loadFrom();
    this.getMake();
    this.getType();
    this.getOptions();
  }

  loadFrom()
  {
    this.CarForm = this.formBuilder.group({
      chassisNo: ['', [Validators.required, Validators.pattern('^[+]?[0-9a-zA-Z .-]*$'), Validators.maxLength(20)]],
      draft: [false],
      details:  this.formBuilder.group({
        make: ['', Validators.required],
        name: ['', Validators.required],
        type: ['', Validators.required],
        modelYear: ['', Validators.required],
        color: ['', Validators.required],
        engineNo: ['', Validators.required],
        mileage: ['', Validators.required],
        cc: ['', Validators.required],
        transmission: [''],
        fuelType: [''],
        option: [[]],
        options: [this.option]
      })
    });
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
  getOptions()
  {
    // tslint:disable-next-line:max-line-length
    this.optionsForDropDown = [{value: 'WOODEN_PANEL', display: 'Wooden Panel'}, {value: 'CRUISE_CONTROL', display: 'Cruise Control'}, {value: 'HIGH_FLOOR', display: 'High Floor'}, {value: 'RAIN_SHED', display: 'Rain Shed'}, {value: 'DVD_PLAYER', display: 'DVD Player'}, {value: 'BACK_CAMERA', display: 'Back Camera'}, {value: 'FOUR_CAMERAS', display: 'Four Cameras'}, {value: 'SUNROOF', display: 'Sun Roof'}, {value: 'WINKER_MIRROR', display: 'Winker Mirror'}, {value: 'AC', display: 'AC'}, {value: 'REAR_AC', display: 'Rear AC'}, {value: 'THIRD_ROW_SEATS', display: 'Third Row Seat'}, {value: 'AIR_BAG', display: 'Air Bag'}, {value: 'HIGH_ROOF', display: 'High Roof'}, {value: 'LOW_ROOF', display: 'Low Roof'}, {value: 'WOODEN_STEERING', display: 'Wooden Steering'}, {value: 'LOW_FLOOR', display: 'Low Floor'}, {value: 'ROOF_RAIL', display: 'Roof Rail'}];

    // tslint:disable-next-line:max-line-length
    // this.options = ['WOODEN_PANEL', 'CRUISE_CONTROL', 'HIGH_FLOOR', 'RAIN_SHED', 'DVD_PLAYER', 'BACK_CAMERA',  'FOUR_CAMERAS', 'SUNROOF', 'WINKER_MIRROR', 'AC', 'REAR_AC', 'THIRD_ROW_SEATS', 'AIR_BAG', 'HIGH_ROOF', 'LOW_ROOF', 'WOODEN_STEERING', 'LOW_FLOOR', 'ROOF_RAIL'];
  }

  onAdd($event)
  {
    this.option.push($event.value);
    // console.log(this.CarForm.value);
  }

  onSubmit()
  {
    if (this.CarForm.get('chassisNo').hasError('required'))
    {
      this.toastrService.error('Chassis No Required!', 'Error!');
      return;
    }
    if (this.CarForm.invalid && !this.CarForm.get('draft').value)
    {
      this.toastrService.error('Please Fill All Field!', 'Error!');
      return;
    }
    if (this.CarForm.get('draft').value)
    {
      this.CarForm.removeControl('details');
    }
    // this.CarForm.patchValue({
    //   options: this.selectedtag
    // });
    console.log(this.CarForm.value);
    this.carService.addCar(this.CarForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Successfully Added Car', 'Success!');
                              this.ngOnInit();
                              this.route.navigate(['/car/list']);
                            },
                            error => {
                              this.toastrService.error(error.error, 'Error!');
                              console.log(error);
                            }
                          );
  }

  toUpperCase_make(data: any)
  {
    this.CarForm.patchValue({
      make: data.toUpperCase()
    });
  }
  toUpperCase_type(data: any)
  {
    this.CarForm.patchValue({
      type: data.toUpperCase()
    });
  }

  back()
  {
    this.location.back();
  }

}

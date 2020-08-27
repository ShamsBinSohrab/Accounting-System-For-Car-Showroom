import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CarService } from 'src/app/car_showroom_accounting_system/Services/car.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent implements OnInit {
  CarForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private carService: CarService,
    private route: Router,
    ) { }

  ngOnInit(): void {
    this.loadFrom();
  }

  loadFrom()
  {
    this.CarForm = this.formBuilder.group({
      chassisNo: ['', [Validators.required, Validators.pattern('^[+]?[0-9a-zA-Z .-]*$'), Validators.maxLength(20)]],
      // Draft: [false],
      details:  this.formBuilder.group({
        make: ['', Validators.required],
        name: ['', Validators.required],
        type: ['', Validators.required],
        modelYear: ['', Validators.required],
        color: ['', Validators.required],
      })
    });
  }

  onSubmit()
  {
    if (this.CarForm.invalid)
    {
      this.toastrService.warning('Please Fill All Field!', 'Warning!');
      return;
    }
    this.carService.addCar(this.CarForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Successfully Added Car', 'Success!');
                            },
                            error => {
                              this.toastrService.error(error, 'Error!');
                              console.log(error.error);
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
    this.route.navigate(['/car/list']);
  }

}

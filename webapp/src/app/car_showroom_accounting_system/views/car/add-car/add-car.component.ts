import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CarService } from 'src/app/car_showroom_accounting_system/Services/car.service';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent implements OnInit {
  CarForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private carService: CarService
    ) { }

  ngOnInit(): void {
    this.loadFrom();
  }

  loadFrom()
  {
    this.CarForm = this.formBuilder.group({
      chassisNo: ['', [Validators.required, Validators.pattern('^[+]?[0-9a-zA-Z .-]*$'), Validators.maxLength(20)]],
      // Draft: [false],
      // Details:  this.formBuilder.group({
        make: ['', Validators.required],
        name: ['', Validators.required],
        type: ['', Validators.required],
        modelYear: ['', Validators.required],
        color: ['', Validators.required],
      // })
    });
  }

  onSubmit()
  {
    this.carService.addCar(this.CarForm.value)
                          .subscribe(
                            data => {
                              this.toastr.success('Successfully Added Car', 'Success!');
                            },
                            error => {
                              this.toastr.error(error, 'Error!');
                            }
                          );
  }

  toUpperCase_make(data: any)
  {
    console.log(data);
    // this.CarForm.patchValue({
    //   make: data.
    // });
  }

}

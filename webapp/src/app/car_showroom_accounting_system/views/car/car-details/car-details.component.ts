import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarService } from 'src/app/car_showroom_accounting_system/Services/car.service';
import { ToastrService } from 'ngx-toastr';
import { Location } from '@angular/common';

@Component({
  selector: 'app-car-details',
  templateUrl: './car-details.component.html',
  styleUrls: ['./car-details.component.scss']
})
export class CarDetailsComponent implements OnInit {
  id: any;
  data: any;
  options: any = '';

  constructor(
    private activeRoute: ActivatedRoute,
    private carService: CarService,
    private router: Router,
    private toastrService: ToastrService,
    private location: Location
    ) { }

  ngOnInit() {
    // this.id = this.activeRoute.snapshot.paramMap.get('carId');
    this.getDetails();
  }

  getDetails()
  {
    this.carService.getCarById()
                  .subscribe(
                    data => {
                      this.data = data;
                      // console.log(this.data);
                      for (const index of data.details.options) {
                        this.options += index.replace('_', ' ');
                        this.options += ', ';
                      }
                      this.options = this.options.substring(0, this.options.lastIndexOf(','));
                    },
                    error => {
                      this.toastrService.error(error.error);
                    }
                    );
  }

  back()
  {
    this.location.back();
  }

  updateCar()
  {
    this.router.navigate(['/car/update']);
  }

}

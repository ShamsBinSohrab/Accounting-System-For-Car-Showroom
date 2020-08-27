import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarService } from 'src/app/car_showroom_accounting_system/Services/car.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-car-details',
  templateUrl: './car-details.component.html',
  styleUrls: ['./car-details.component.scss']
})
export class CarDetailsComponent implements OnInit {
  id: any;
  data: any;

  constructor(
    private activeRoute: ActivatedRoute,
    private carService: CarService,
    private router: Router,
    private toastrService: ToastrService
    ) { }

  ngOnInit() {
    this.id = this.activeRoute.snapshot.paramMap.get('carId');
    this.getDetails(this.id);
  }

  getDetails(carId: any)
  {
    this.carService.getCarById(carId)
                  .subscribe(
                    data => {
                      this.data = data;
                    },
                    error => {
                      this.toastrService.error(error.error);
                    }
                    );
  }

  back()
  {
    this.router.navigate(['/car/list']);
  }

  updateCar()
  {
    const carId = this.id;
    this.router.navigate(['/car/update', { carId }]);
  }

}

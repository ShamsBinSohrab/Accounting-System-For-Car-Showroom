import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CarListComponent } from './car-list/car-list.component';
import { AddCarComponent } from './add-car/add-car.component';
import { CarDetailsComponent } from './car-details/car-details.component';
import { CarUpdateComponent } from './car-update/car-update.component';

const routes: Routes = [
  {
    path: '',
    data: {
      Title: 'Car'
    },
    children: [
      {
        path: 'list',
        component: CarListComponent
      },
      {
        path: 'create',
        component: AddCarComponent
      },
      {
        path: 'details',
        component: CarDetailsComponent
      },
      {
        path: 'update',
        component: CarUpdateComponent
      },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarRoutingModule { }

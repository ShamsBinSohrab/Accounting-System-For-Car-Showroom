import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarListComponent } from './car-list/car-list.component';
import { CarRoutingModule } from './car-routing.module';
import { AddCarComponent } from './add-car/add-car.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    CarRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    CarListComponent,
    AddCarComponent
  ]
})
export class CarModule { }

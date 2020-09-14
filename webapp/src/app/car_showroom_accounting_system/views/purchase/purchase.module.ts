import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PurchaseRoutingModule } from './purchase-routing.module';
import { PurchaseListComponent } from './purchase-list/purchase-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PurchaseAddComponent } from './purchase-add/purchase-add.component';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { PurchaseDetailsComponent } from './purchase-details/purchase-details.component';
import { PurchaseUpdateComponent } from './purchase-update/purchase-update.component';

@NgModule({
  imports: [
    CommonModule,
    PurchaseRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BsDatepickerModule,
  ],
  declarations: [
    PurchaseListComponent,
    PurchaseAddComponent,
    PurchaseDetailsComponent,
    PurchaseUpdateComponent
  ]
})
export class PurchaseModule { }

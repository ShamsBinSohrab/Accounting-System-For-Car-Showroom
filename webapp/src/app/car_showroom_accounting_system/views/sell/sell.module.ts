import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SellRoutingModule } from './sell-routing.module';
import { SellListComponent } from './sell-list/sell-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { SellUpdateComponent } from './sell-update/sell-update.component';
import { SellAddComponent } from './sell-add/sell-add.component';
import { SellDetailsComponent } from './sell-details/sell-details.component';


@NgModule({
  imports: [
    CommonModule,
    SellRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BsDatepickerModule
  ],
  declarations: [
    SellListComponent,
    SellUpdateComponent,
    SellAddComponent,
    SellDetailsComponent
  ]
})
export class SellModule { }

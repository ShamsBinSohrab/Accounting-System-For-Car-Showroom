import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExpenseRoutingModule } from './expense-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ExpenseUpdateComponent } from './expense-update/expense-update.component';
import { ExpenseListComponent } from './expense-list/expense-list.component';
import { ExpenseAddComponent } from './expense-add/expense-add.component';
import { ExpenseDetailsComponent } from './expense-details/expense-details.component';

@NgModule({
  imports: [
    CommonModule,
    ExpenseRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BsDatepickerModule,
  ],
  declarations: [
    ExpenseListComponent,
    ExpenseAddComponent,
    ExpenseDetailsComponent,
    ExpenseUpdateComponent
  ]
})
export class ExpenseModule { }

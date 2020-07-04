import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BillPayment, BillPaymentRoutingModule } from '../bill-payment/bill-payment-routing.module';


@NgModule({
  declarations: [BillPayment],
  imports: [
    CommonModule,
    BillPaymentRoutingModule
  ]
})
export class BillPaymentModule { }

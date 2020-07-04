import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BeneficiaryComponent } from './beneficiary/beneficiary.component';
import { OtpComponent } from './otp/otp.component';
import { HistoryComponent } from './history/history.component';
import { ExecuteTransferComponent } from './execute-transfer/execute-transfer.component';
import { TransferRoutingModule } from './transfer-routing.module';



@NgModule({
  declarations: [
       BeneficiaryComponent,
       OtpComponent,
       HistoryComponent,
       ExecuteTransferComponent
      ],
  imports: [
    CommonModule,
    TransferRoutingModule
  ]
})
export class TransferModule { }

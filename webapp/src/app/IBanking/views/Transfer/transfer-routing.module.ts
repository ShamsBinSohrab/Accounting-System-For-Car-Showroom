import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BeneficiaryComponent } from './beneficiary/beneficiary.component';
import { OtpComponent } from './otp/otp.component';
import { ExecuteTransferComponent } from './execute-transfer/execute-transfer.component';
import { HistoryComponent } from './history/history.component';

const routes: Routes = [
  {
    path:'',
    redirectTo: 'beneficiary',
    pathMatch: 'full',
  },
  {
    path:"",
    data:{
      title:"Transfer"
    },
    children:[
      {
        path:"beneficiary",
        component: BeneficiaryComponent,
        data: {
          title: 'Beneficiary'
        }
      }, 
      {
        path:"otp",
        component: OtpComponent,
        data: {
          title: 'OTP'
        }
      }, 
      {
        path:"execute-transfer",
        component: ExecuteTransferComponent,
        data: {
          title: 'Execute Transfer'
        }
      },
      {
        path:"history",
        component: HistoryComponent,
        data: {
          title: 'History'
        }
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TransferRoutingModule { }
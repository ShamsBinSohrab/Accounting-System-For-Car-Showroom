import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DescoComponent } from './desco/desco.component';
import { DpdcComponent } from './dpdc/dpdc.component';
import { BtclComponent } from './btcl/btcl.component';

const routes: Routes = [{
    path:'',
    redirectTo: 'desco',
    pathMatch: 'full',
  },
  {
    path:"",
    data:{
      title:"Bill-Payment"
    },
    children:[
      {
        path:"desco",
        component: DescoComponent,
        data: {
          title: 'DESCO'
        }
      }, 
      {
        path:"dpdc",
        component: DpdcComponent,
        data: {
          title: 'DPDC'
        }
      }, 
      {
        path:"btcl",
        component: BtclComponent,
        data: {
          title: 'BTCL'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BillPaymentRoutingModule { }

export const BillPayment = [DescoComponent, DpdcComponent, BtclComponent];
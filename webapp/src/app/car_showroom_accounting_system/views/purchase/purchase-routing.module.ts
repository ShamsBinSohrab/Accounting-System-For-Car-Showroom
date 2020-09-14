import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PurchaseListComponent } from './purchase-list/purchase-list.component';
import { PurchaseAddComponent } from './purchase-add/purchase-add.component';
import { PurchaseDetailsComponent } from './purchase-details/purchase-details.component';
import { PurchaseUpdateComponent } from './purchase-update/purchase-update.component';

const routes: Routes = [
  {
    path: '',
    data: {
      Title: 'Purchase Record'
    },
    children: [
      {
        path: 'list',
        component: PurchaseListComponent
      },
      {
        path: 'create',
        component: PurchaseAddComponent
      },
      {
        path: 'details',
        component: PurchaseDetailsComponent
      },
      {
        path: 'update',
        component: PurchaseUpdateComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PurchaseRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SellListComponent } from './sell-list/sell-list.component';
import { SellUpdateComponent } from './sell-update/sell-update.component';
import { SellAddComponent } from './sell-add/sell-add.component';
import { SellDetailsComponent } from './sell-details/sell-details.component';

const routes: Routes = [
  {
    path: '',
    data: {
      Title: 'Sell Record'
    },
    children: [
      {
        path: 'list',
        component: SellListComponent
      },
      {
        path: 'create',
        component: SellAddComponent
      },
      {
        path: 'details',
        component: SellDetailsComponent
      },
      {
        path: 'update',
        component: SellUpdateComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SellRoutingModule {

}

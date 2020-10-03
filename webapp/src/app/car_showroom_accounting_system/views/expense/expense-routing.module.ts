import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ExpenseListComponent } from './expense-list/expense-list.component';
import { ExpenseAddComponent } from './expense-add/expense-add.component';
import { ExpenseDetailsComponent } from './expense-details/expense-details.component';
import { ExpenseUpdateComponent } from './expense-update/expense-update.component';

const routes: Routes = [
  {
    path: '',
    data: {
      Title: 'Expense Record'
    },
    children: [
      {
        path: 'list',
        component: ExpenseListComponent
      },
      {
        path: 'create',
        component: ExpenseAddComponent
      },
      {
        path: 'details',
        component: ExpenseDetailsComponent
      },
      {
        path: 'update',
        component: ExpenseUpdateComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExpenseRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { OperatorListComponent } from './operator-list/operator-list.component';
import { OperatorAddComponent } from './operator-add/operator-add.component';
import { OperatorUpdateComponent } from './operator-update/operator-update.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

const routes: Routes = [
  {
    path: '',
    data: {
      Title: 'Operator'
    },
    children: [
      {
        path: 'list',
        component: OperatorListComponent
      },
      {
        path: 'create',
        component: OperatorAddComponent
      },
      // {
      //   path: 'details',
      //   // component: Operator
      // },
      {
        path: 'update',
        component: OperatorUpdateComponent
      },
      {
        path: 'reset-password',
        component: ResetPasswordComponent
      },
      {
        path: 'change-password',
        component: ChangePasswordComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperatorRoutingModule { }

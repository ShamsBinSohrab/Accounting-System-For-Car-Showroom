import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ConfirmResetPasswordComponent } from './confirm-reset-password/confirm-reset-password.component';

const routes: Routes = [
  {
    path: '',
    data: {
      Title: 'Forgot Password'
    },
    children: [
      {
        path: 'email',
        component: ForgotPasswordComponent
      },
      {
        path: 'confirm-reset-password',
        component: ConfirmResetPasswordComponent
      },
      {
        path: 'confirm-page',
        component: ConfirmResetPasswordComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ForgotPasswordRoutingModule { }

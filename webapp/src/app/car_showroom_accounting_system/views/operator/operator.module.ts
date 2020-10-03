import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OperatorListComponent } from './operator-list/operator-list.component';
import { OperatorUpdateComponent } from './operator-update/operator-update.component';
import { OperatorAddComponent } from './operator-add/operator-add.component';
import { OperatorRoutingModule } from './operator-routing.module';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    OperatorRoutingModule
  ],
  declarations: [
    OperatorListComponent,
    OperatorUpdateComponent,
    OperatorAddComponent,
    ResetPasswordComponent,
    ChangePasswordComponent
  ]
})
export class OperatorModule { }

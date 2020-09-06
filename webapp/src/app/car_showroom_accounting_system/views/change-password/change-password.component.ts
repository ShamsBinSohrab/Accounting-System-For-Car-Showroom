import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ChangePasswordService } from 'src/app/car_showroom_accounting_system/Services/change-password.service';
import { EncryptionDescryptionService } from 'src/app/car_showroom_accounting_system/Services/encryption-descryption.service';
import { AlertifyService } from '../../Common/alertify.service';
import {ToastrService} from 'ngx-toastr';
import { LoginService } from '../../Services/login.service';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  ChangePassword: FormGroup;
  dummyObj: any;
  message: any;
  resultType = false;

  constructor(
    private fb: FormBuilder,
    private changePasswordService: ChangePasswordService,
    private encObj: EncryptionDescryptionService,
    private router: Router,
    private toastr: ToastrService,
    private loginService: LoginService
    ) {}

  ngOnInit(): void {
    this.ChangePassword = this.fb.group({
      oldPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required, Validators.maxLength(14), Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.maxLength(14), Validators.minLength(6)]]
    });
  }

onSubmit()
{
    if (this.ChangePassword.get('newPassword').value !== this.ChangePassword.get('confirmPassword').value) {
      this.toastr.warning('Confirm password not matched', 'Warning!');
      return;
    }

    if (this.ChangePassword.get('oldPassword').hasError('required')) {
      this.toastr.warning('Old Password can not be empty!', 'Warning!');
      return;
    }
    if (this.ChangePassword.get('newPassword').hasError('required')) {
      this.toastr.warning('New Password can not be empty!', 'Warning!');
      return;
    }
    if (this.ChangePassword.get('newPassword').hasError('minlength')) {
      this.toastr.warning('New Password is between 6-14 charecters!', 'Warning!');
      return;
    }
    if (this.ChangePassword.get('newPassword').hasError('maxlength')) {
      this.toastr.warning('New Password is between 6-14 charecters!', 'Warning!');
      return;
    }
    if (this.ChangePassword.get('confirmPassword').hasError('required')) {
      this.toastr.warning('Confirm Password can not be empty!', 'Warning!');
      return;
    }
    if (this.ChangePassword.get('confirmPassword').hasError('minlength')) {
      this.toastr.warning('Confirm Password is between 6-14 charecters!', 'Warning!');
      return;
    }
    if (this.ChangePassword.get('confirmPassword').hasError('maxlength')) {
      this.toastr.warning('Confirm Password is between 6-14 charecters!', 'Warning!');
      return;
    }

    if (this.ChangePassword.invalid) {
      this.toastr.warning('Please fill the form correctly', 'Warning!');
      return;
    }

    this.changePasswordService.changePassword(this.ChangePassword.value)
                              .subscribe(
                                success => {
                                  this.toastr.success('Password Reset Successful.', 'Success!');
                                  this.loginService.logout();
                                },
                                error => {
                                  this.toastr.error(error.error, 'Error!');
                                }
                              );
}

}

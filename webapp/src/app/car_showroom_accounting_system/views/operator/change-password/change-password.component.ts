import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EncryptionDescryptionService } from 'src/app/car_showroom_accounting_system/Services/encryption-descryption.service';
import { LoginService } from 'src/app/car_showroom_accounting_system/Services/login.service';
import { OperatorService } from 'src/app/car_showroom_accounting_system/Services/operator.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  ChangePassword: FormGroup;
  dummyObj: any;
  message: any;
  resultType = false;

  constructor(
    private fb: FormBuilder,
    private operatorService: OperatorService,
    private encObj: EncryptionDescryptionService,
    private router: Router,
    private toastr: ToastrService,
    private loginService: LoginService,
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

    this.operatorService.changePassword(this.ChangePassword.value)
                              .subscribe(
                                success => {
                                  this.toastr.success('Password Changed Successful.', 'Success!');
                                  this.loginService.logout();
                                },
                                error => {
                                  this.toastr.error(error.error, 'Error!');
                                }
                              );
}

}

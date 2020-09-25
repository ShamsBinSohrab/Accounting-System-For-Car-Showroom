import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Location } from '@angular/common';
import { OperatorService } from 'src/app/car_showroom_accounting_system/Services/operator.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  ResetPasswordForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private operatorService: OperatorService,
    private route: Router,
    private location: Location
    ) { }

  ngOnInit(): void {
    this.loadFrom();
  }

  loadFrom()
  {
    this.ResetPasswordForm = this.formBuilder.group({
      newPassword: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(6)]],
    });
  }

  onSubmit()
  {
    if (this.ResetPasswordForm.get('newPassword').value !== this.ResetPasswordForm.get('confirmPassword').value)
    {
      this.toastrService.warning('Confirm Password Not Matched', 'Error!');
      return;
    }
    if (this.ResetPasswordForm.invalid)
    {
      this.toastrService.warning('Please Fill All Field!', 'Error!');
      return;
    }
    console.log(this.ResetPasswordForm.value);
    this.operatorService.resetPassword(this.ResetPasswordForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Successfully Reset Password', 'Success!');
                              this.ngOnInit();
                              this.location.back();
                            },
                            error => {
                              this.toastrService.error(error.error, 'Error!');
                              console.log(error.error);
                            }
                          );
  }

  back()
  {
    this.location.back();
  }
}

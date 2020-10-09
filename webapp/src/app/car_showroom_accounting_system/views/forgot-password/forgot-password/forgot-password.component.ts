import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Location } from '@angular/common';
import { ForgotPasswordService } from 'src/app/car_showroom_accounting_system/Services/forgot-password.service';
@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  ForgotPasswordForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private forgotService: ForgotPasswordService,
    private route: Router,
    private location: Location
    ) { }

  ngOnInit(): void {
    this.loadFrom();
  }

  loadFrom()
  {
    this.ForgotPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.maxLength(100)]],
    });
  }

  onSubmit()
  {
    if (this.ForgotPasswordForm.invalid)
    {
      this.toastrService.error('Email is required', 'Error!');
      return;
    }
    this.forgotService.forgotPassword(this.ForgotPasswordForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Please Check Your Email.', 'Success!');
                              this.ngOnInit();
                              this.route.navigate(['/forgot-password/confirm-page']);
                            },
                            error => {
                              this.toastrService.error(error.error, 'Error!');
                              console.log(error);
                            }
                          );
  }

  back()
  {
    this.route.navigate(['/login']);
  }
}

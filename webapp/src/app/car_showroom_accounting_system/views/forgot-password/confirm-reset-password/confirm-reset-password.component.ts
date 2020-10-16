import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ForgotPasswordService } from 'src/app/car_showroom_accounting_system/Services/forgot-password.service';
import Swal from 'sweetalert2';
import {Location} from '@angular/common';

@Component({
  selector: 'app-dashboard',
  templateUrl: './confirm-reset-password.component.html',
  styleUrls: ['./confirm-reset-password.component.scss']
})
export class ConfirmResetPasswordComponent implements OnInit {

  ForgotPasswordForm: FormGroup;
  token: any;

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private forgotService: ForgotPasswordService,
    private route: Router,
    private location: Location,
    private activeRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.token = this.activeRoute.snapshot.queryParams.token;
    this.loadFrom();
  }
  loadFrom()
  {
    this.ForgotPasswordForm = this.formBuilder.group({
      newPassword: ['', [Validators.required, Validators.maxLength(14), Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.maxLength(14), Validators.minLength(6)]],
    });
  }

  onSubmit()
  {
    if (this.ForgotPasswordForm.invalid)
    {
      this.toastrService.error('All field is required, Max Length 14, Min Length 6', 'Error!');
      return;
    }
    this.forgotService.confirmResetPassword(this.ForgotPasswordForm.value, this.token)
                          .subscribe(
                            data => {
                              // this.toastrService.success('Please Check Your Email.', 'Success!');
                              Swal.fire(
                                'Success!',
                                'Password Reset Done. Please Login.',
                                'success'
                              ).then((result) => {
                                /* Read more about isConfirmed, isDenied below */
                                if (result.isConfirmed) {
                                  this.ngOnInit();
                                  this.back();
                                } else if (result.isDenied) {
                                  this.ngOnInit();
                                  this.back();
                                }
                              });
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

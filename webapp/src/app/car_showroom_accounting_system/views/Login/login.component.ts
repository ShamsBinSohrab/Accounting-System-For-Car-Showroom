import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/car_showroom_accounting_system/Services/login.service';
import { EncryptionDescryptionService } from 'src/app/car_showroom_accounting_system/Services/encryption-descryption.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-dashboard',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  dummyObj: any;
  loading = false;
  submitted = false;
  errorMessage = '';

  constructor(
                private formBuilder: FormBuilder,
                private loginService: LoginService,
                private encryptObj: EncryptionDescryptionService,
                private router: Router,
                private toastr: ToastrService
      ) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
  });
  }
  get username()
  {
    return this.loginForm.get('username');
  }
  get password()
  {
    return this.loginForm.get('password');
  }
  onSubmit()
  {
    // this.dummyObj = {
    //   username: this.encryptObj.encryptData(this.username.value),
    //   password: this.encryptObj.encryptData(this.password.value)
    // };

    if (this.loginForm.get('username').hasError('required')) {
      this.toastr.warning('Username Can not be empty.', 'Warning!');
      return;
    }
    if (this.loginForm.get('password').hasError('required')) {
      this.toastr.warning('Password Can not be empty.', 'Warning!');
      return;
    }

    this.loginService.authenticate(this.loginForm.value)
                            .subscribe(
                                      data => {
                                        this.router.navigate(['/']);

                                      },
                                      error => {
                                          console.log(error);
                                          this.toastr.error(error.error);
                                      });
  }

}

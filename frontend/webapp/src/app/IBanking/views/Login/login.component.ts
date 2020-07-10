import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/IBanking/Services/login.service';
import { EncryptionDescryptionService } from 'src/app/IBanking/Services/encryption-descryption.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-dashboard',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  dummyObj;
  loading = false;
  submitted = false;
  errorMessage="";

  constructor(
                private _formBuilder: FormBuilder,
                private loginService:LoginService,
                private encryptObj:EncryptionDescryptionService,
                private router: Router,
                private toastr: ToastrService
      ) { }
  
  ngOnInit(): void {
    this.loginForm = this._formBuilder.group({
      username: ['', [Validators.required,Validators.pattern]],
      password: ['', [Validators.required,Validators.pattern]]
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
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;

    this.dummyObj={
      username: this.encryptObj.encryptData(this.username.value),
      password: this.encryptObj.encryptData(this.password.value)
    };

    // console.log(this.encryptObj.encryptData("Noyon892"));
    //  console.log(this.encryptObj.encryptData("123456"));
    
    //console.log(this.dummyObj);

     this.loginService.authenticate(this.dummyObj)
                            .subscribe(                                                        
                                      data => {                                  
                                        this.router.navigate(['/']);
                                      },
                                      error => {
                                          this.errorMessage=error.error;
                                          this.toastr.error(error.error);
                                      });
  }

}

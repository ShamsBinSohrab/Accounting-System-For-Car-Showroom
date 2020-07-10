import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ChangePasswordService } from 'src/app/IBanking/Services/change-password.service';
import { EncryptionDescryptionService } from 'src/app/IBanking/Services/encryption-descryption.service';
import { AlertifyService } from '../../Common/alertify.service';
import {ToastrService} from 'ngx-toastr';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  
  ChangePassword:FormGroup;
  dummyObj:any;
  message: any;
  resultType: boolean=false;

  constructor(
    private fb:FormBuilder,
    private changePasswordService:ChangePasswordService,
    private encObj:EncryptionDescryptionService,
    private router:Router,
    private alertify:AlertifyService,
    private toastr:ToastrService
    ) {}

  ngOnInit(): void {
    this.ChangePassword=this.fb.group({
      oldpassword:['',[Validators.required,Validators.pattern]],
      newpassword:['',[Validators.required,Validators.pattern]],
      newconfirmpassword:['',[Validators.required,Validators.pattern]]
    });
  }


get oldpassword()
{
  return this.ChangePassword.get('oldpassword');
}

get newpassword()
{
  return this.ChangePassword.get('newpassword');
}

get newconfirmpassword()
{
  return this.ChangePassword.get('newconfirmpassword');
}


onSubmit()
{
    if (this.ChangePassword.invalid) {
      this.toastr.warning('Please fill the form correctly',"Warning!");
      return;
    }

    if (this.newpassword.value!=this.newconfirmpassword.value) {
      this.toastr.warning('Confirm password not matched',"Warning!");
      return;
    }

    this.dummyObj={
      username:this.encObj.encryptData(localStorage.getItem('username')),
      oldpassword: this.encObj.encryptData(this.oldpassword.value),
      newpassword: this.encObj.encryptData(this.newpassword.value),
      newconfirmpassword: this.encObj.encryptData(this.newconfirmpassword.value),
    }
    this.changePasswordService.changePassword(this.dummyObj)
                              .subscribe(
                                success=>{                          
                                  this.ChangePassword.reset();
                                  this.alertify.success(success.message);                                  
                                  this.toastr.success(success.message,"Success!");
                                },
                                error=>{
                                  this.alertify.error(error.error);
                                  this.toastr.error(error.error,'Error!')
                                }                                
                              );
}

}

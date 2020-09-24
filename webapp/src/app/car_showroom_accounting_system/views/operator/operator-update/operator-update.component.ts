import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Location } from '@angular/common';
import { OperatorService } from 'src/app/car_showroom_accounting_system/Services/operator.service';

@Component({
  selector: 'app-operator-update',
  templateUrl: './operator-update.component.html',
  styleUrls: ['./operator-update.component.scss']
})
export class OperatorUpdateComponent implements OnInit {

  OperatorForm: FormGroup;
  role: string[];
  data: any;

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private operatorService: OperatorService,
    private route: Router,
    private location: Location
    ) { }

  ngOnInit(): void {
    this.loadFrom();
    this.getOperatorDetails();
    this.getRole();
  }

  loadFrom()
  {
    this.OperatorForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern('^[+]?[0-9a-zA-Z]*$'), Validators.maxLength(20)]],
      role: ['', [Validators.required]],
    });
  }

  getOperatorDetails()
  {
    this.operatorService.getOperatorById()
                          .subscribe(
                            data => {
                              this.OperatorForm.patchValue({
                                username: data.username,
                                role: data.role
                              });
                            },
                            error => {
                              this.toastrService.error(error.error, 'Error!');
                              console.log(error.error);
                            }
                          );
  }

  getRole()
  {
    this.role = ['ADMIN', 'SALES_OPERATOR', 'ACCOUNTS_OPERATOR'];
  }

  onSubmit()
  {
    if (this.OperatorForm.invalid)
    {
      this.toastrService.warning('Please Fill All Field!', 'Warning!');
      return;
    }
    console.log(this.OperatorForm.value);
    this.operatorService.updateOperator(this.OperatorForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Successfully Updated Operator', 'Success!');
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

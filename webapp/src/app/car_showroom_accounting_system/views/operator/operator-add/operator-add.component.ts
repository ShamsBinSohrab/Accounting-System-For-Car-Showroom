import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { OperatorService } from 'src/app/car_showroom_accounting_system/Services/operator.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-operator-add',
  templateUrl: './operator-add.component.html',
  styleUrls: ['./operator-add.component.scss']
})
export class OperatorAddComponent implements OnInit {

  OperatorForm: FormGroup;
  role: string[];

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private operatorService: OperatorService,
    private route: Router,
    private location: Location
    ) { }

  ngOnInit(): void {
    this.loadFrom();
    this.getRole();
  }

  loadFrom()
  {
    this.OperatorForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern('^[+]?[0-9a-zA-Z]*$'), Validators.maxLength(20)]],
      password: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(6)]],
      role: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.maxLength(100)]],
    });
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
    this.operatorService.addOperator(this.OperatorForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Successfully Added Operator', 'Success!');
                              this.ngOnInit();
                              this.route.navigate(['/operator/list']);
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

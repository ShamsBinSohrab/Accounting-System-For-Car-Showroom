import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CompanyService } from 'src/app/car_showroom_accounting_system/Services/company.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.scss']
})
export class AddCompanyComponent implements OnInit {

  CompanyForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private companyService: CompanyService,
    private route: Router,
    private location: Location
    ) { }

  ngOnInit(): void {
    this.loadFrom();
  }

  loadFrom()
  {
    this.CompanyForm = this.formBuilder.group({
      companyName: ['', [Validators.required, Validators.pattern('^[+]?[0-9a-zA-Z .-]*$'), Validators.maxLength(100)]],
      active: [true],
    });
  }

  onSubmit()
  {
    if (this.CompanyForm.invalid)
    {
      this.toastrService.warning('Please Fill All Field!', 'Warning!');
      return;
    }
    console.log(this.CompanyForm.value);
    this.companyService.addCompany(this.CompanyForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Successfully Added Company', 'Success!');
                              this.ngOnInit();
                              this.route.navigate(['/company/list']);
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

  addCompany()
  {
    this.route.navigate(['/company/create']);
  }

}

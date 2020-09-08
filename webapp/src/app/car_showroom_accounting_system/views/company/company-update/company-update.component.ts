import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CompanyService } from 'src/app/car_showroom_accounting_system/Services/company.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-company-update',
  templateUrl: './company-update.component.html',
  styleUrls: ['./company-update.component.css']
})
export class CompanyUpdateComponent implements OnInit {

  CompanyForm: FormGroup;
  id: string;

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private companyService: CompanyService,
    private route: Router,
    private location: Location,
    private activeRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.loadForm();
    this.id = this.activeRoute.snapshot.paramMap.get('companyId');
    this.loadFormData();
  }

  loadForm()
  {
    this.CompanyForm = this.formBuilder.group({
      companyName: ['', [Validators.required, Validators.pattern('^[+]?[0-9a-zA-Z .-]*$'), Validators.maxLength(100)]],
      active: [true],
    });
  }

  loadFormData()
  {
    this.companyService.getCompanybyId(this.id)
                          .subscribe(
                            data => {
                              this.CompanyForm.patchValue({
                                companyName: data.companyName,
                                active: data.active,
                              });
                            },
                            error => {
                              this.toastrService.error(error.error, 'Error!');
                              console.log(error.error);
                            }
                          );
  }

  onSubmit()
  {
    if (this.CompanyForm.invalid)
    {
      this.toastrService.warning('Please Fill All Field!', 'Warning!');
      return;
    }
    this.companyService.updateCompany(this.id, this.CompanyForm.value)
                          .subscribe(
                            data => {
                              this.toastrService.success('Successfully Update Company', 'Success!');
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

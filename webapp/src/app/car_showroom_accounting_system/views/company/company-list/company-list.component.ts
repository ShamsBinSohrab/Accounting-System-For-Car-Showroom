import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CompanyService } from 'src/app/car_showroom_accounting_system/Services/company.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { DefaultLayoutComponent } from 'src/app/car_showroom_accounting_system/containers';

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.scss']
})
export class CompanyListComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private companyService: CompanyService,
    private route: Router,
    private location: Location,
    private defaultComponent: DefaultLayoutComponent
    ) { }

  ngOnInit() {
  }

  getToken(id: any)
  {
    this.companyService.getCompanyToken(id)
                      .subscribe(
                        data => {
                          this.toastrService.success('Company Assigned', 'Success!');
                          this.defaultComponent.ngOnInit();
                          console.log(data);
                        },
                        error => {
                          this.toastrService.error(error.error, 'Error!');
                          console.log(error.error);
                        }
                      );
  }

  resetCompany()
  {
    localStorage.setItem('company_token', '');
    this.toastrService.success('Company Reset Done', 'Success!');
    this.defaultComponent.ngOnInit();
  }

}

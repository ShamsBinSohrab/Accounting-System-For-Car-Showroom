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
  companyList: any;
  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private companyService: CompanyService,
    private route: Router,
    private location: Location,
    private defaultComponent: DefaultLayoutComponent
    ) { }

  ngOnInit() {
    this.getCompanyList();
  }

  getCompanyList()
  {
    const parameter = 'page=0';
    this.companyService.getCompanyList(parameter)
                       .subscribe(
                          data => {
                            this.companyList = data;
                          },
                          error => {
                            this.toastrService.error(error.error, 'Error!');
                          }
                         );
  }

  getToken(id: any)
  {
    this.companyService.getCompanyToken(id)
                      .subscribe(
                        data => {
                          this.defaultComponent.ngOnInit();
                          this.route.navigate(['/car/list']);
                        },
                        error => {
                          this.toastrService.error(error.error, 'Error!');
                          console.log(error.error);
                        }
                      );
  }

  createCompany()
  {
    this.route.navigate(['/company/create']);
  }

  updateCompany(companyId: any)
  {
    this.route.navigate(['/company/update/', { companyId }]);
  }

}

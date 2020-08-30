import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddCompanyComponent } from './add-company/add-company.component';
import { RouterModule, Routes } from '@angular/router';
import { CompanyListComponent } from './company-list/company-list.component';

const routes: Routes = [
  {
    path: '',
    data: {
      Title: 'Company'
    },
    children: [
      {
        path: 'list',
        component: CompanyListComponent
      },
      {
        path: 'create',
        component: AddCompanyComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyRoutingModule { }

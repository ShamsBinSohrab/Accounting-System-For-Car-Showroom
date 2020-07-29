import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { SummeryComponent } from './summery/summery.component';
import { AccountsComponent } from './accounts/accounts.component';
import { LiquidationinterestaccountComponent } from './liquidationinterestaccount/liquidationinterestaccount.component';
import { TermsanddemandloansComponent } from './termsanddemandloans/termsanddemandloans.component';

const routes: Routes = [
  {
    path:"",
    data:{
      Title:"Customer Position"
    },
    children:[
      {
        path:"summery",
        component: SummeryComponent
      }, 
      {
        path:"accounts",
        component: AccountsComponent
      }, 
      {
        path:"liquidationinterestaccount",
        component: LiquidationinterestaccountComponent
      },
      {
        path:"termsanddemandloans",
        component: TermsanddemandloansComponent
      },
     
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class CustomerpositionRoutingModule { }

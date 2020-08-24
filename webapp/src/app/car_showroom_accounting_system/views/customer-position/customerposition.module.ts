import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SummeryComponent } from './summery/summery.component';
import { AccountsComponent } from './accounts/accounts.component';
import { LiquidationinterestaccountComponent } from './liquidationinterestaccount/liquidationinterestaccount.component';
import { TermsanddemandloansComponent } from './termsanddemandloans/termsanddemandloans.component';
import { CustomerpositionRoutingModule } from './customerposition-routing.module';



@NgModule({
  declarations: [
    SummeryComponent,
    AccountsComponent,
    LiquidationinterestaccountComponent,
    TermsanddemandloansComponent
  ],
  imports: [
    CommonModule,
    CustomerpositionRoutingModule
  ]
})
export class CustomerpositionModule { }

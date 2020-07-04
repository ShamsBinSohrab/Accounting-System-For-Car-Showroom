import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TermsConditionComponent } from './terms-condition/terms-condition.component';
import { FaqComponent } from './faq/faq.component';
import { TermsConditionRoutingModule } from './terms-condition-routing.module';



@NgModule({
  declarations: [
    TermsConditionComponent,
    FaqComponent
  ],
  imports: [
    CommonModule,
    TermsConditionRoutingModule
  ]
})
export class TermsConditionModule { }

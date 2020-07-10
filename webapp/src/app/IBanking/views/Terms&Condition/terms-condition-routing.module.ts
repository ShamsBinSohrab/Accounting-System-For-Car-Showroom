import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { TermsConditionComponent } from './terms-condition/terms-condition.component';
import { FaqComponent } from './faq/faq.component';

const routes:Routes=[{
  path:'',
  redirectTo: 'terms&condition',
  pathMatch: 'full',
},
{
  path:"",
  data:{
    title:"Terms & Condition"
  },
  children:[
    {
      path:"terms&condition",
      component: TermsConditionComponent,
      data: {
        title: 'Terms & Condition'
      }
    }, 
    {
      path:"faq",
      component: FaqComponent,
      data: {
        title: 'FAQ'
      }
    }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TermsConditionRoutingModule { }

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CompanyService } from 'src/app/car_showroom_accounting_system/Services/company.service';
import { Router } from '@angular/router';
import { DefaultLayoutComponent } from 'src/app/car_showroom_accounting_system/containers';
import {Location} from '@angular/common';
import { PurchaseService } from 'src/app/car_showroom_accounting_system/Services/purchase.service';

@Component({
  selector: 'app-purchase-list',
  templateUrl: './purchase-list.component.html',
  styleUrls: ['./purchase-list.component.scss']
})
export class PurchaseListComponent implements OnInit {

  purchaseList: any;
  constructor(
    private toastrService: ToastrService,
    private purchaseService: PurchaseService,
    private route: Router,
    private location: Location
    ) { }

  ngOnInit() {
    this.getPurchaseList();
  }

  getPurchaseList()
  {
    const parameter = 'page=0';
    this.purchaseService.getPurchaseList(parameter)
                       .subscribe(
                          data => {
                            console.log(data);
                            this.purchaseList = data;
                          },
                          error => {
                            this.toastrService.error(error.error, 'Error!');
                            console.log(error.error);
                          }
                         );
  }

  deletePurchase(link: string)
  {
    this.purchaseService.deletePurchase(link)
    .subscribe(
      data => {
        this.toastrService.success('Purchase Record Delete Successful.', 'Success');
        this.ngOnInit();
      },
      error => {
        this.toastrService.error(error.error);
      }
    );
  }

  purchaseDetails(link: string)
  {
    localStorage.setItem('link', link);
    this.route.navigate(['/purchase/details']);
  }
  updatePurchase(link: string)
  {
    localStorage.setItem('link', link);
    this.route.navigate(['/purchase/update']);
  }

  createPurchase()
  {
    this.route.navigate(['/purchase/create']);
  }
}

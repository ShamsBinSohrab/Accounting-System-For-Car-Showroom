import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PurchaseService } from 'src/app/car_showroom_accounting_system/Services/purchase.service';

@Component({
  selector: 'app-purchase-update',
  templateUrl: './purchase-update.component.html',
  styleUrls: ['./purchase-update.component.scss']
})
export class PurchaseUpdateComponent implements OnInit {

  PurchaseForm: FormGroup;
  data: any;

  constructor(
    private location: Location,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private purchaseService: PurchaseService,
    private route: Router,
  ) { }

  ngOnInit() {
    this.loadFrom();
    this.getDetails();
  }

  getDetails()
  {
    this.purchaseService.getPurchaseById()
                  .subscribe(
                    data => {
                      console.log(data);
                      this.data = data;
                      this.PurchaseForm.patchValue({
                          chassisNo: data.chassisNo,
                          purchaseType: data.purchaseType,
                          purchaseDate: data.purchaseDate,
                          basePrice: data.basePrice,
                          lcCharge: data.lcCharge,
                          shippingCharge: data.shippingCharge,
                          tax: data.tax,
                          advancedIncomeTax: data.advancedIncomeTax,
                          cnfCharge: data.cnfCharge,
                          transportationCharge: data.transportationCharge,
                          garageCharge: data.garageCharge,
                          miscellaneousCharge: data.miscellaneousCharge,
                      });
                    },
                    error => {
                      this.toastrService.error(error.error);
                    }
                    );
  }

  loadFrom()
  {
    this.PurchaseForm = this.formBuilder.group({
      chassisNo: ['', [Validators.required]],
      purchaseType: ['', [Validators.required]],
      purchaseDate: ['', [Validators.required]],
      basePrice: ['', Validators.required],
      lcCharge: ['', Validators.required],
      shippingCharge: [''],
      tax: [''],
      advancedIncomeTax: [''],
      cnfCharge: [''],
      transportationCharge: [''],
      garageCharge: [''],
      miscellaneousCharge: [''],
    });
  }

onSubmit()
{
  if (this.PurchaseForm.get('purchaseType').hasError('required'))
  {
    this.toastrService.error('Purchase Type Required!', 'Error!');
    return;
  }
  if (this.PurchaseForm.get('purchaseDate').hasError('required'))
  {
    this.toastrService.error('Purchase Date Required!', 'Error!');
    return;
  }
  if (this.PurchaseForm.get('basePrice').hasError('required'))
  {
    this.toastrService.error('Base Price Required!', 'Error!');
    return;
  }
  if (this.PurchaseForm.get('lcCharge').hasError('required'))
  {
    this.toastrService.error('LC Charge Required!', 'Error!');
    return;
  }
  this.purchaseService.updatePurchase(this.PurchaseForm.value)
                        .subscribe(
                          data => {
                            this.toastrService.success('Successfully Updated Purchase Record', 'Success!');
                            this.ngOnInit();
                            this.route.navigate(['/purchase/list']);
                          },
                          error => {
                            this.toastrService.error(error.error, 'Error!');
                            console.log(error);
                          }
                        );
}

back()
{
  this.location.back();
}



}

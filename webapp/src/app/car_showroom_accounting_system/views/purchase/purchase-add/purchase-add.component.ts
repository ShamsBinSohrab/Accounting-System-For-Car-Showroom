import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PurchaseService } from 'src/app/car_showroom_accounting_system/Services/purchase.service';

@Component({
  selector: 'app-purchase-add',
  templateUrl: './purchase-add.component.html',
  styleUrls: ['./purchase-add.component.scss']
})
export class PurchaseAddComponent implements OnInit {

  PurchaseForm: FormGroup;

  constructor(
    private location: Location,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private purchaseService: PurchaseService,
    private route: Router,
  ) { }

  ngOnInit() {
    this.loadFrom();
  }

  loadFrom()
  {
    this.PurchaseForm = this.formBuilder.group({
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
  this.purchaseService.addPurchase(this.PurchaseForm.value)
                        .subscribe(
                          data => {
                            this.toastrService.success('Successfully Added Purchase Record', 'Success!');
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

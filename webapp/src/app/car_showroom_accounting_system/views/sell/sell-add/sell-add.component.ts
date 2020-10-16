import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SellService } from 'src/app/car_showroom_accounting_system/Services/sell.service';

@Component({
  selector: 'app-sell-add',
  templateUrl: './sell-add.component.html',
  styleUrls: ['./sell-add.component.scss']
})
export class SellAddComponent implements OnInit {

  SellForm: FormGroup;

  constructor(
    private location: Location,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private sellService: SellService,
    private route: Router,
  ) { }

  ngOnInit() {
    this.loadFrom();
  }

  loadFrom()
  {
    this.SellForm = this.formBuilder.group({
      sellType: ['', [Validators.required]],
      sellDate: ['', [Validators.required]],
      paidAsAdvance: ['', Validators.required],
      paidOnDelivery: ['', Validators.required],
      paidAfterDelivery: [''],
      purchaseLetterNo: ['']
    });
  }

onSubmit()
{
  if (this.SellForm.get('sellType').hasError('required'))
  {
    this.toastrService.error('Sell Type Required!', 'Error!');
    return;
  }
  if (this.SellForm.get('sellDate').hasError('required'))
  {
    this.toastrService.error('Sell Date Required!', 'Error!');
    return;
  }
  if (this.SellForm.get('paidAsAdvance').hasError('required'))
  {
    this.toastrService.error('Paid as Advanced Required!', 'Error!');
    return;
  }
  if (this.SellForm.get('paidOnDelivery').hasError('required'))
  {
    this.toastrService.error('Paid On Delivery Required!', 'Error!');
    return;
  }
  this.sellService.addSell(this.SellForm.value)
                        .subscribe(
                          data => {
                            this.toastrService.success('Successfully Added Sell Record', 'Success!');
                            this.ngOnInit();
                            this.route.navigate(['/sell/list']);
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

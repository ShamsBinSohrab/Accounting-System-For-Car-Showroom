import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SellService } from '../../../Services/sell.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-sell-update',
  templateUrl: './sell-update.component.html',
  styleUrls: ['./sell-update.component.scss']
})
export class SellUpdateComponent implements OnInit {
  SellForm: FormGroup;
  data: any;

  constructor(
    private location: Location,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private sellService: SellService,
    private route: Router,
  ) { }

  ngOnInit() {
    this.loadFrom();
    this.getDetails();
  }

  getDetails()
  {
    this.sellService.getSellById()
                  .subscribe(
                    data => {
                      this.data = data;
                      this.SellForm.patchValue({
                          sellType: data.sellType,
                          sellDate: data.sellDate,
                          paidAsAdvance: data.paidAsAdvance,
                          paidOnDelivery: data.paidOnDelivery,
                          paidAfterDelivery: data.paidAfterDelivery,
                          purchaseLetterNo: data.purchaseLetterNo,
                      });
                    },
                    error => {
                      this.toastrService.error(error.error);
                    }
                    );
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
  this.sellService.updateSell(this.SellForm.value)
                        .subscribe(
                          data => {
                            this.toastrService.success('Successfully Updated Sell Record', 'Success!');
                            this.ngOnInit();
                            this.back();
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

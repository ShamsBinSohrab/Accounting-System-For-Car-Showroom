import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PurchaseService } from 'src/app/car_showroom_accounting_system/Services/purchase.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-purchase-details',
  templateUrl: './purchase-details.component.html',
  styleUrls: ['./purchase-details.component.scss']
})
export class PurchaseDetailsComponent implements OnInit {

  data: any;

  constructor(
    private purchaseService: PurchaseService,
    private router: Router,
    private toastrService: ToastrService,
    private location: Location
    ) { }

  ngOnInit() {
    // this.id = this.activeRoute.snapshot.paramMap.get('carId');
    this.getDetails();
  }

  getDetails()
  {
    this.purchaseService.getPurchaseById()
                  .subscribe(
                    data => {
                      this.data = data;
                    },
                    error => {
                      this.toastrService.error(error.error);
                    }
                    );
  }

  back()
  {
    this.location.back();
  }

  updatePurchase(link: any)
  {
    localStorage.setItem('link', link);
    this.router.navigate(['/purchase/update']);
  }

}

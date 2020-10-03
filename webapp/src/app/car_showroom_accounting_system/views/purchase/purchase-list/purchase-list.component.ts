import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { PurchaseService } from 'src/app/car_showroom_accounting_system/Services/purchase.service';
import Swal from 'sweetalert2';
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
    private route: Router    ) { }

  ngOnInit() {
    this.getPurchaseList();
  }

  getPurchaseList()
  {
    const parameter = 'page=0';
    this.purchaseService.getPurchaseList(parameter)
                       .subscribe(
                          data => {
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
    Swal.fire({
    title: 'Are you sure?',
    text: 'You won\'t be able to revert this!',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#B3BBC2',
    confirmButtonText: 'Yes, delete it!'
  }).then((result) => {
    if (result.isConfirmed) {
      this.purchaseService.deletePurchase(link)
          .subscribe(
            () => {
              // Swal.fire(
              //   'Deleted!',
              //   'Purchase Record Delete Successful.',
              //   'success'
              // );
              this.toastrService.success('Purchase Record Delete Successful.', 'Success');
              this.ngOnInit();
            },
            error => {
              this.toastrService.error(error.error);
            }
          );
    }
  });
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

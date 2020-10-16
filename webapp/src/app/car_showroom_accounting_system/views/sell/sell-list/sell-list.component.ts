import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SellService } from 'src/app/car_showroom_accounting_system/Services/sell.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sell-list',
  templateUrl: './sell-list.component.html',
  styleUrls: ['./sell-list.component.scss']
})
export class SellListComponent implements OnInit {

  sellList:any
  constructor(
    private toastrService: ToastrService,
    private sellService: SellService,
    private route: Router  ) { }

  ngOnInit() {
    this.getSellList();
  }
  getSellList()
  {
    const parameter = 'page=0';
    this.sellService.getSellList(parameter)
                       .subscribe(
                          data => {
                            this.sellList = data;
                          },
                          error => {
                            this.toastrService.error(error.error, 'Error!');
                            console.log(error.error);
                          }
                         );
  }

  deleteSell(link: string)
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
      this.sellService.deleteSell(link)
          .subscribe(
            () => {
              // Swal.fire(
              //   'Deleted!',
              //   'Purchase Record Delete Successful.',
              //   'success'
              // );
              this.toastrService.success('Sell Record Delete Successful.', 'Success');
              this.ngOnInit();
            },
            error => {
              this.toastrService.error(error.error);
            }
          );
    }
  });
  }

  sellDetails(link: string)
  {
    localStorage.setItem('link', link);
    this.route.navigate(['/sell/details']);
  }
  updateSell(link: string)
  {
    localStorage.setItem('link', link);
    this.route.navigate(['/sell/update']);
  }

  createSell()
  {
    this.route.navigate(['/sell/create']);
  }

}

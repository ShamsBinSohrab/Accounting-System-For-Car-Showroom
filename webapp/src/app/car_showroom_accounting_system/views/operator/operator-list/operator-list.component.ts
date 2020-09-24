import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { OperatorService } from 'src/app/car_showroom_accounting_system/Services/operator.service';

@Component({
  selector: 'app-operator-list',
  templateUrl: './operator-list.component.html',
  styleUrls: ['./operator-list.component.scss']
})
export class OperatorListComponent implements OnInit {

  data: Array<any>;
  constructor(
                private formBuilder: FormBuilder,
                private operatorService: OperatorService,
                private router: Router,
                private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.data = new Array();
    this.getOperatorList();
  }

  getOperatorList()
  {
    this.operatorService.getOperatorList()
        .subscribe(
          data => {
            this.data = data;
            console.log(this.data);
          },
          error => {
            this.toastrService.error(error.error, 'Error!');
          }
        );
  }
  deleteOperator(link: string)
  {
    this.operatorService.deleteOperator(link)
    .subscribe(
      data => {
        this.toastrService.success('Delete Successful', 'Success');
        this.ngOnInit();
      },
      error => {
        this.toastrService.error(error.error);
      }
    );
  }

  operatorDetails(link: string)
  {
    localStorage.setItem('link', link);
    // this.router.navigate(['/car/details', { carId }]);
    this.router.navigate(['/operator/details']);
  }
  updateOperator(link: string)
  {
    localStorage.setItem('link', link);
    this.router.navigate(['/operator/update']);
    // this.router.navigate(['/car/update', { carId }]);
  }
  addOperator()
  {
    this.router.navigate(['/operator/create']);
  }
  operatorAction(link: any, status: any)
  {
    localStorage.setItem('link', link);
    if (status === 'changePassword')
    {
      this.router.navigate(['/operator/change-password']);
    }
    else
    {
      this.router.navigate(['/operator/reset-password']);
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ExpenseService } from 'src/app/car_showroom_accounting_system/Services/expense.service';
import {Location} from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-expense-list',
  templateUrl: './expense-list.component.html',
  styleUrls: ['./expense-list.component.scss']
})
export class ExpenseListComponent implements OnInit {

  expenseList: any;
  constructor(
    private toastrService: ToastrService,
    private expenseService: ExpenseService,
    private route: Router,
    private location: Location
    ) { }

  ngOnInit() {
    this.getPurchaseList();
  }

  getPurchaseList()
  {
    const parameter = 'page=0';
    this.expenseService.getExpenseList(parameter)
                       .subscribe(
                          data => {
                            console.log(data);
                            this.expenseList = data;
                          },
                          error => {
                            this.toastrService.error(error.error, 'Error!');
                            console.log(error.error);
                          }
                         );
  }

  deleteExpense(link: string)
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
    this.expenseService.deleteExpense(link)
    .subscribe(
      data => {
        this.toastrService.success('Expense Record Delete Successful.', 'Success');
        this.ngOnInit();
      },
      error => {
        this.toastrService.error(error.error);
      }
    );
    }
  });
  }

  expenseDetails(link: string)
  {
    localStorage.setItem('link', link);
    this.route.navigate(['/expense/details']);
  }
  updateExpense(link: string)
  {
    localStorage.setItem('link', link);
    this.route.navigate(['/expense/update']);
  }

  createExpense()
  {
    this.route.navigate(['/expense/create']);
  }
}

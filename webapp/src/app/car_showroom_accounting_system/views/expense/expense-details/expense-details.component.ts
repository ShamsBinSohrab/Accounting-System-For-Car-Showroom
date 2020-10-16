import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ExpenseService } from 'src/app/car_showroom_accounting_system/Services/expense.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-expense-details',
  templateUrl: './expense-details.component.html',
  styleUrls: ['./expense-details.component.scss']
})
export class ExpenseDetailsComponent implements OnInit {

  id: any;
  data: any;
  options: any = '';

  constructor(
    private activeRoute: ActivatedRoute,
    private expenseService: ExpenseService,
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
    this.expenseService.getExpenseById()
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

  updateExpense()
  {
    this.router.navigate(['/expense/update']);
  }
}

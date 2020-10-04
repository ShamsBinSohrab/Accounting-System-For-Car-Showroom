import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Location} from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { ExpenseService } from 'src/app/car_showroom_accounting_system/Services/expense.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-expense-update',
  templateUrl: './expense-update.component.html',
  styleUrls: ['./expense-update.component.scss']
})
export class ExpenseUpdateComponent implements OnInit {

  ExpenseForm: FormGroup;
  expenseType: string[];
  expenseDate: any;

  constructor(
    private location: Location,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private expenseService: ExpenseService,
    private route: Router,
  ) { }

  ngOnInit() {
    this.getExpenseType();
    this.loadFrom();
    this.getSellData();
  }
  getSellData() {
    this.expenseService.getExpenseById()
                          .subscribe(
                            data => {
                              this.ExpenseForm.patchValue({
                                expenseDate: data.expenseDate,
                                recipient: data.recipient,
                                amount: data.amount,
                                expenseType: data.expenseType,
                                notes: data.notes
                              });
                              this.expenseDate = data.expenseDate;
                            },
                            error => {
                              this.toastrService.error(error.error, 'Error!');
                              console.log(error.error);
                            }
                          );
  }

  getExpenseType()
  {
    this.expenseType = ['RENT', 'MAINTENANCE', 'TAX', 'TRAVEL', 'FOOD', 'SALARY', 'INSURANCE'];
  }

  loadFrom()
  {
    this.ExpenseForm = this.formBuilder.group({
      expenseDate: ['', [Validators.required]],
      recipient: ['', Validators.required],
      amount: ['', Validators.required],
      expenseType: ['', Validators.required],
      notes: ['']
    });
  }

onSubmit()
{
  if (this.ExpenseForm.get('expenseType').hasError('required'))
  {
    this.toastrService.error('Expense Type Required!', 'Error!');
    return;
  }
  if (this.ExpenseForm.get('expenseDate').hasError('required'))
  {
    this.toastrService.error('Expense Date Required!', 'Error!');
    return;
  }
  if (this.ExpenseForm.get('recipient').hasError('required'))
  {
    this.toastrService.error('Recipient Required!', 'Error!');
    return;
  }
  if (this.ExpenseForm.get('amount').hasError('required'))
  {
    this.toastrService.error('Amount Required!', 'Error!');
    return;
  }
  this.expenseService.updateExpense(this.ExpenseForm.value)
                        .subscribe(
                          data => {
                            this.toastrService.success('Successfully Updated Expense Record', 'Success!');
                            this.ngOnInit();
                            this.location.back();
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

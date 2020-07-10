import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import * as jsPDF from 'jspdf';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-account-statement',
  templateUrl: './account-statement.component.html',
  styleUrls: ['./account-statement.component.css']
})
export class AccountStatementComponent implements OnInit {

  constructor() { }

  divHide=false;

  Data = [
    { Id: 101, Name: 'Nitin', Salary: 35000 },
    { Id: 102, Name: 'Sonu', Salary: 25000 },
    { Id: 103, Name: 'Mohit', Salary: 30000 },
    { Id: 104, Name: 'Rahul', Salary: 20000 },
    { Id: 105, Name: 'Kunal', Salary: 40000 }
  ];

  @ViewChild('content') content: ElementRef<any>;
  @ViewChild('TABLE') TABLE: ElementRef<any>;
  title = 'Excel';

  divShow()
  {
    this.divHide=true;
  }
  reset()
  {
    this.divHide=false;
  }

  ExportTOExcel() {
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(this.TABLE.nativeElement);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, 'ScoreSheet.xlsx');
  }

  public SavePDF(): void {
    const content = this.content.nativeElement;
    const doc = new jsPDF();
    // tslint:disable-next-line: variable-name
    const _elementHandlers = {
      '#editor'(element, renderer) {
        return true;
      }
    };
    doc.fromHTML(content.innerHTML, 15, 15, {

      width: 190,
      elementHandlers: _elementHandlers
    });
    doc.save('test.pdf');
  }

  ngOnInit(): void {
  }

}

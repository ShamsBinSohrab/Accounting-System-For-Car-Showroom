import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SellService } from '../../../Services/sell.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-sell-details',
  templateUrl: './sell-details.component.html',
  styleUrls: ['./sell-details.component.scss']
})
export class SellDetailsComponent implements OnInit {

  data: any;

  constructor(
    private sellService: SellService,
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
    this.sellService.getSellById()
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

  updateSell(link: any)
  {
    localStorage.setItem('link', link);
    this.router.navigate(['/sell/update']);
  }


}

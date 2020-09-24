import { Component, OnInit } from '@angular/core';
import { navItems, saNavItems, saCompanyNavItems } from 'src/app/_nav';
import { LoginService } from 'src/app/car_showroom_accounting_system/Services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.css']
})
export class DefaultLayoutComponent implements OnInit {

  public sidebarMinimized = false;
  public navItems: any;
  homelink = '';
  username: any;
  constructor(
              private loginSercice: LoginService,
              private router: Router
              ) {
                  console.log(localStorage.getItem('username'));
                  this.username = localStorage.getItem('username');
               }

  ngOnInit(): void {
    if (localStorage.getItem('company_token') !== '' && localStorage.getItem('user_role') === 'SUPER_ADMIN')
    {
      this.navItems = saCompanyNavItems;
    }
    else if (localStorage.getItem('company_token') === '')
    {
      this.navItems = saNavItems;
    }
    else
    {
      this.navItems = navItems;
    }
  }


  toggleMinimize(e) {
    this.sidebarMinimized = e;
  }

  logout() {
    this.loginSercice.logout();
}

}

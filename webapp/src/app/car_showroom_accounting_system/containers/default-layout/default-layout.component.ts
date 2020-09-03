import { Component, OnInit } from '@angular/core';
import { navItems, saNavItems } from 'src/app/_nav';
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


  constructor(
              private loginSercice: LoginService,
              private router: Router
              ) { }

  ngOnInit(): void {
    if (localStorage.getItem('company-token') != null)
    {
      this.navItems = navItems;
    }
    else
    {
      this.navItems = saNavItems;
    }
  }


  toggleMinimize(e) {
    this.sidebarMinimized = e;
  }

  logout() {
    this.loginSercice.logout();
    this.router.navigate(['/login']);
}

}

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { PerfectScrollbarModule, PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';

import {
  AppAsideModule,
  AppBreadcrumbModule,
  AppHeaderModule,
  AppFooterModule,
  AppSidebarModule,
} from '@coreui/angular';

// Import 3rd party components
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ChartsModule } from 'ng2-charts';
import { ToastrModule } from 'ngx-toastr';

// Custom Import
import { DefaultLayoutComponent } from './IBanking/containers';
import { AuthGuard } from './IBanking/Helpers/auth.guard';
import { AuthInterceptor } from './IBanking/Helpers/auth.interceptor';
import { MaterialModule } from './IBanking/Common/material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './IBanking/views/Home/home.component';
import { P404Component } from './IBanking/views/error/404.component';
import { P500Component } from './IBanking/views/error/500.component';
import { LoginComponent } from './IBanking/views/Login/login.component';
import { SignupComponent } from './IBanking/views/signup/signup.component';
import { LoginService } from './IBanking/Services/login.service';
import { EncryptionDescryptionService } from './IBanking/Services/encryption-descryption.service';
import { ChangePasswordComponent } from './IBanking/views/change-password/change-password.component';
import { DashboardComponent } from './IBanking/views/dashboard/dashboard.component';
import { AccountStatementComponent } from './IBanking/views/account-statement/account-statement.component';
import { ContactComponent } from './IBanking/views/contact/contact.component';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

const APP_CONTAINERS = [
  DefaultLayoutComponent
];

@NgModule({
  declarations: [
    AppComponent,
    ...APP_CONTAINERS,
    P404Component,
    P500Component,
    HomeComponent,
    LoginComponent,
    SignupComponent,
    ChangePasswordComponent,
    DashboardComponent,
    AccountStatementComponent,
    ContactComponent,
    
    
  ],
  imports: [
    AppAsideModule,
    AppBreadcrumbModule.forRoot(),
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    BsDropdownModule.forRoot(),
    TabsModule.forRoot(),
    ChartsModule,
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    AppRoutingModule,
    FormsModule, 
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot({
      timeOut:5000,
      positionClass:'toast-top-right',
      preventDuplicates:false
    }),
  ],
  providers: [
    LoginService,
    EncryptionDescryptionService,
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

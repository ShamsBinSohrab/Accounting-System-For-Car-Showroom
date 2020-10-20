import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { LocationStrategy, PathLocationStrategy } from '@angular/common';
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
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ChartsModule } from 'ng2-charts';
import { ToastrModule } from 'ngx-toastr';

// Custom Import
import { DefaultLayoutComponent } from './car_showroom_accounting_system/containers';
import { AuthGuard } from './car_showroom_accounting_system/Helpers/auth.guard';
import { AuthInterceptor } from './car_showroom_accounting_system/Helpers/auth.interceptor';
import { MaterialModule } from './car_showroom_accounting_system/Common/material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './car_showroom_accounting_system/views/Home/home.component';
import { P404Component } from './car_showroom_accounting_system/views/error/404.component';
import { P500Component } from './car_showroom_accounting_system/views/error/500.component';
import { LoginComponent } from './car_showroom_accounting_system/views/Login/login.component';
import { SignupComponent } from './car_showroom_accounting_system/views/signup/signup.component';
import { LoginService } from './car_showroom_accounting_system/Services/login.service';
import { EncryptionDescryptionService } from './car_showroom_accounting_system/Services/encryption-descryption.service';
import { DashboardComponent } from './car_showroom_accounting_system/views/dashboard/dashboard.component';
import { ContactComponent } from './car_showroom_accounting_system/views/contact/contact.component';
import { TagInputModule } from 'ngx-chips';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { NgHttpLoaderModule } from 'ng-http-loader';
import { ForgotPasswordComponent } from './car_showroom_accounting_system/views/forgot-password/forgot-password/forgot-password.component';
import { ConfirmResetPasswordComponent } from './car_showroom_accounting_system/views/forgot-password/confirm-reset-password/confirm-reset-password.component';

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
    DashboardComponent,
    ContactComponent,
    ForgotPasswordComponent,
    ConfirmResetPasswordComponent
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
    BsDatepickerModule.forRoot(),
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-right',
      preventDuplicates: false
    }),
    TagInputModule,
    SweetAlert2Module.forRoot(),
    NgHttpLoaderModule.forRoot()
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
      useClass: PathLocationStrategy
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

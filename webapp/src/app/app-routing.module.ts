import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './car_showroom_accounting_system/views/Home/home.component';
import { LoginComponent } from './car_showroom_accounting_system/views/Login/login.component';
import { SignupComponent } from './car_showroom_accounting_system/views/signup/signup.component';
import { AuthGuard } from './car_showroom_accounting_system/Helpers/auth.guard';
import { DefaultLayoutComponent } from './car_showroom_accounting_system/containers';
import { P404Component } from './car_showroom_accounting_system/views/error/404.component';
import { P500Component } from './car_showroom_accounting_system/views/error/500.component';
import { ChangePasswordComponent } from './car_showroom_accounting_system/views/change-password/change-password.component';
import { DashboardComponent } from './car_showroom_accounting_system/views/dashboard/dashboard.component';
import { ContactComponent } from './car_showroom_accounting_system/views/contact/contact.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: '404',
    component: P404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '500',
    component: P500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'signup',
    component: SignupComponent,
    data: {
      title: 'Register Page'
    }
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    canActivate: [AuthGuard],
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'car',
        loadChildren: () => import('./car_showroom_accounting_system/views/car/car.module').then(m => m.CarModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./car_showroom_accounting_system/views/company/company.module').then(m => m.CompanyModule)
      },
      {
        path: 'change-password',
        component: ChangePasswordComponent
      },
      {
        path: 'dashboard',
        component: DashboardComponent
      },
      {
        path: 'contact',
        component: ContactComponent,
        data:
        {
          title: 'Contact'
        }
      },
      {
        path: 'terms',
        // tslint:disable-next-line:max-line-length
        loadChildren: () => import('./car_showroom_accounting_system/views/Terms&Condition/terms-condition.module').then(e => e.TermsConditionModule),
      },



      // Theme Path
      {
        path: 'buttons',
        loadChildren: () => import('./views/buttons/buttons.module').then(m => m.ButtonsModule),
      },
      {
        path: 'base',
        loadChildren: () => import('./views/base/base.module').then(m => m.BaseModule)
      },
      {
        path: 'charts',
        loadChildren: () => import('./views/chartjs/chartjs.module').then(m => m.ChartJSModule)
      },
      {
        path: 'icons',
        loadChildren: () => import('./views/icons/icons.module').then(m => m.IconsModule)
      },
      {
        path: 'notifications',
        loadChildren: () => import('./views/notifications/notifications.module').then(m => m.NotificationsModule)
      },
      {
        path: 'theme',
        loadChildren: () => import('./views/theme/theme.module').then(m => m.ThemeModule)
      },
      {
        path: 'widgets',
        loadChildren: () => import('./views/widgets/widgets.module').then(m => m.WidgetsModule)
      }
    ]
  },
  { path: '**', component: P404Component }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

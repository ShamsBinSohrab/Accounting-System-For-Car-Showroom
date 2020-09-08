import {HttpInterceptor, HttpRequest, HttpHandler, HttpUserEvent, HttpEvent} from '@angular/common/http';
import 'rxjs/add/operator/do';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor
{
    constructor(private router: Router)
    {}
    intercept(req: HttpRequest<any>, next: HttpHandler): import('rxjs').Observable<HttpEvent<any>> {
        if (req.headers.get('No-Auth') === 'True') {
            return next.handle(req.clone());
        }
        if (localStorage.getItem('auth_token') != null) {
            // let clonedreq = null;
            // if (req.headers.get('No-Company-Token') === 'True') {
            const clonedreq = req.clone({
                headers: req.headers.set('Authorization', 'Bearer ' + localStorage.getItem('auth_token'))
                .set('x-company-accessor-token', localStorage.getItem('company_token'))
            });
            // }
            // else
            // {
            //   clonedreq = req.clone({
            //     headers: req.headers.set('Authorization', 'Bearer ' + localStorage.getItem('auth_token'))
            //     .set('x-company-accessor-token', localStorage.getItem('company_token'))
            //   });
            // }
            return next.handle(clonedreq)
                .do(
                succ => { },
                err => {
                    if (err.status === 401) {
                        this.router.navigateByUrl('/login');
                    }
                }
                );
            }
            else {
                this.router.navigateByUrl('/login');
            }
    }
}

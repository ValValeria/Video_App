import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

@Injectable({
  providedIn: "root"
})
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let objectAuth = JSON.stringify({});

    const key = 'auth';
    const localItem = localStorage.getItem(key);

    if (localItem != null) {
      objectAuth = localItem;
    }

    const reqAuth = req.clone({
      headers: new HttpHeaders({
        "Authorization": objectAuth
      })
    });

    return next.handle(reqAuth);
  }
}

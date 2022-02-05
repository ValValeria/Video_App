import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

@Injectable({providedIn: "root"})
export class JwtInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const refreshToken = localStorage.getItem("refreshToken") ?? "";
    const accessToken = localStorage.getItem("accessToken") ?? "";

    const cloneRequest = req.clone({
      headers: req.headers
        .set("refreshToken", refreshToken)
        .set("accessToken", accessToken)
    });

    return next.handle(cloneRequest);
  }
}

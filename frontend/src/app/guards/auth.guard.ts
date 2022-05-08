import {Injectable} from '@angular/core';
import {CanLoad, Route, UrlSegment, UrlTree} from '@angular/router';

import {Observable, of} from 'rxjs';

import {UserService} from "../services/user.service";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class AuthGuard implements CanLoad {
  public constructor(
    private userService: UserService,
    private httpClient: HttpClient
  ) {
  }

  canLoad(route: Route, segments: UrlSegment[]): Observable<boolean | UrlTree> {
    return of(this.userService.user.isAuth);
  }
}

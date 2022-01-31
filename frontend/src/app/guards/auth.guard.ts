import { Injectable } from '@angular/core';
import { CanLoad, Route, UrlSegment, UrlTree} from '@angular/router';

import { Observable } from 'rxjs';

import { UserService } from "../services/user.service";

@Injectable()
export class AuthGuard implements CanLoad {
  public constructor(private userService: UserService) {
  }

  canLoad(route: Route, segments: UrlSegment[]): Observable<boolean | UrlTree>{
    return Observable.create(this.userService.user.isAuth);
  }
}

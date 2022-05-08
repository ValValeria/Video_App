import {CanLoad, Route, UrlSegment, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserService} from "../services/user.service";

@Injectable()
export class OnlyAnonimGuard implements CanLoad {
  public constructor(private userService: UserService) {
  }

  canLoad(
    route: Route,
    segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return !this.userService.user.isAuth;
  }
}

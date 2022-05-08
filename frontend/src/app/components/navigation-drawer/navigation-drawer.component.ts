import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {authFormSubject$} from "../../subjects/auth-form.subject";

type pathType = {path: string, title: string, onlyAuth?: boolean, adminOnly?: boolean};

@Component({
  selector: 'app-navigation-drawer',
  templateUrl: './navigation-drawer.component.html',
  styleUrls: ['./navigation-drawer.component.scss']
})
export class NavigationDrawerComponent implements AfterViewInit {
  public readonly paths: pathType[];

  public constructor(
    private router: Router,
    private userService: UserService
  ) {
    this.paths = [
      {path: '', title: 'Home'},
      {path: 'auth', title: 'Authenticate', onlyAuth: false},
      {path: 'videos', title: 'Videos', onlyAuth: true},
      {path: 'contacts', title: 'Contact us'}
    ];
  }

  ngAfterViewInit(): void {
    authFormSubject$.subscribe(this.changeMenu.bind(this));
  }

  private changeMenu(): void {
    const paths: pathType[] = [];

    for (const path of this.paths) {
      if (path.onlyAuth && this.userService.user.isAuth) {
        paths.push(path);
      }
      else if (!path.onlyAuth && !this.userService.user.isAuth) {
        paths.push(path);
      }
      else if (path.onlyAuth === undefined && path.adminOnly === undefined) {
        paths.push(path);
      }
      else if (path.adminOnly && this.userService.isAdmin()) {
        paths.push(path);
      }
    }

    this.paths.splice(0, this.paths.length);
    this.paths.push(...paths);
  }

  handleClick(url: string): void {
    this.router.navigateByUrl(url).then(r => console.log("Navigated"));
  }
}

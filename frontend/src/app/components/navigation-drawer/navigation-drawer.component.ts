import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";

type pathType = {path: string, title: string, onlyAuth?: boolean};

@Component({
  selector: 'app-navigation-drawer',
  templateUrl: './navigation-drawer.component.html',
  styleUrls: ['./navigation-drawer.component.scss']
})
export class NavigationDrawerComponent implements OnInit {
  public readonly paths: pathType[];

  public constructor(
    private router: Router,
    private userService: UserService
  ) {
    this.paths = [
      {path: '', title: 'Home'},
      {path: 'auth', title: 'Authenticate'},
      {path: 'videos', title: 'Videos', onlyAuth: true},
      {path: 'contacts', title: 'Contact us'}
    ];
  }

  ngOnInit(): void {
    for (const path of this.paths) {
       if (
         (path.onlyAuth && !this.userService.user.isAuth) ||
         (!path.onlyAuth && this.userService.user.isAuth)
       ) {
         this.paths.splice(this.paths.indexOf(path), 1);
       }
    }
  }

  handleClick(url: string): void {
    this.router.navigateByUrl(url).then(r => console.log("Navigated"));
  }
}

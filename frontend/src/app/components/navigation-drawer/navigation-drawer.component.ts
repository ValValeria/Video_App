import {Component} from '@angular/core';
import {Router} from "@angular/router";

type pathType = {path: string, title: string};

@Component({
  selector: 'app-navigation-drawer',
  templateUrl: './navigation-drawer.component.html',
  styleUrls: ['./navigation-drawer.component.scss']
})
export class NavigationDrawerComponent {
  public readonly paths: pathType[];

  public constructor(private router: Router) {
    this.paths = [
      {path: '', title: 'Home'},
      {path: 'auth', title: 'Authenticate'},
      {path: 'videos', title: 'Videos'}
    ];
  }

  handleClick(url: string): void {
    this.router.navigateByUrl(url).then(r => console.log("Navigated"));
  }
}

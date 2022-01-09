import { Component, ViewChild} from '@angular/core';

import { MatDrawer } from "@angular/material/sidenav";
import {Router} from "@angular/router";

type pathType = {path: string, title: string};

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent{
  @ViewChild("drawer", {read: MatDrawer}) matDrawer: MatDrawer | undefined;
  public readonly paths: pathType[];

  public constructor(private router: Router) {
    this.paths = [
      {path: '', title: 'Home'}
    ];
  }

  async handleMenuClick() {
    if (this.matDrawer !== undefined) {
        await this.matDrawer.toggle();
    }
  }

  handleClick(url: string): void {
    this.router.navigateByUrl(url).then(r => console.log("Navigated"));
  }
}

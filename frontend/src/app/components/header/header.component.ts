import {Component} from '@angular/core';

import {menuClick$} from "../../subjects/header-component.subject";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent{
  constructor(
    public userService: UserService,
    private router: Router
  ) { }

  public handleClick(): void {
    menuClick$.next(null);
  }

  public async navigateToHome(): Promise<void> {
    await this.router.navigateByUrl("/");
  }
}

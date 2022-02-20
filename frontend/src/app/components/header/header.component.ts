import {Component} from '@angular/core';

import {menuClick$} from "../../subjects/header-component.subject";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent{
  constructor(
    public userService: UserService
  ) { }

  handleClick() {
    menuClick$.next(null);
  }
}

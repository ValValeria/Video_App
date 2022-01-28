import {Component} from '@angular/core';

import {menuClick$} from "../subjects/header-component.subject";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent{
  constructor() { }

  handleClick() {
    menuClick$.next(null);
  }
}

import {Component, OnInit, ViewChild} from '@angular/core';

import {auditTime} from "rxjs";

import {MatDrawer} from "@angular/material/sidenav";

import {menuClick$} from "./components/subjects/header-component.subject";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  @ViewChild("drawer", {read: MatDrawer}) matDrawer: MatDrawer | undefined;

  ngOnInit(): void {
    menuClick$
      .pipe(auditTime(100))
      .subscribe(async(value) => {
      if (this.matDrawer !== undefined) {
        await this.matDrawer.toggle();
      }
    });
  }
}

import {AfterViewInit, Component, ContentChild, ElementRef, ViewChild} from '@angular/core';
import {MatDrawer} from "@angular/material/sidenav";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent{
  @ContentChild("drawer", {read: MatDrawer}) matDrawer: MatDrawer | undefined;

  async handleMenuClick() {
    if (this.matDrawer !== undefined) {
        await this.matDrawer.toggle();
    }
  }
}

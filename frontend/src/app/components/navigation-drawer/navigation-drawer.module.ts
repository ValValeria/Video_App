import { NgModule } from '@angular/core';

import { NavigationDrawerComponent } from './navigation-drawer.component';
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    NavigationDrawerComponent
  ],
  exports: [
    NavigationDrawerComponent
  ],
  imports: [
    SharedModule
  ]
})
export class NavigationDrawerModule { }

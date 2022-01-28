import { NgModule } from '@angular/core';

import { NavigationDrawerComponent } from './navigation-drawer.component';
import {CoreModule} from "../../core/core.module";

@NgModule({
  declarations: [
    NavigationDrawerComponent
  ],
  exports: [
    NavigationDrawerComponent
  ],
  imports: [
    CoreModule
  ]
})
export class NavigationDrawerModule { }

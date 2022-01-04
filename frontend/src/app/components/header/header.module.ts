import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";

import { HeaderComponent } from './header.component';
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [
    HeaderComponent
  ],
  exports: [
    HeaderComponent
  ],
  imports: [
    BrowserAnimationsModule,
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule
  ]
})
export class HeaderModule { }

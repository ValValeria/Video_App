import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer.component';
import { MatToolbarModule } from "@angular/material/toolbar";
import {MatListModule} from "@angular/material/list";
import {MatGridListModule} from "@angular/material/grid-list";

@NgModule({
  declarations: [
    FooterComponent
  ],
  exports: [
    FooterComponent
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatListModule,
    MatGridListModule
  ]
})
export class FooterModule { }

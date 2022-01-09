import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from "@angular/material/button";
import {MatListModule} from '@angular/material/list';

const modules: any[] = [CommonModule, MatButtonModule, MatListModule];

@NgModule({
  declarations: [],
  exports: [...modules]
})
export class CoreModule { }

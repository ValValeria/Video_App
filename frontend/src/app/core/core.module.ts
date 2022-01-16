import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from "@angular/material/button";
import { MatListModule } from '@angular/material/list';
import { HttpClientModule } from "@angular/common/http";

const modules: any[] = [CommonModule, MatButtonModule, MatListModule,
                        HttpClientModule];

@NgModule({
  declarations: [],
  exports: [...modules]
})
export class CoreModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SearchComponent} from "./search.component";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";

@NgModule({
  declarations: [
    SearchComponent
  ],
    imports: [
        CommonModule,
        SectionLayoutModule
    ]
})
export class SearchModule { }

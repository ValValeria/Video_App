import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SearchComponent} from "./search.component";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {NoResultsModule} from "../../components/no-results/no-results.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {VideoCardModule} from "../../components/video-card/video-card.module";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {path: "", component: SearchComponent}
];

@NgModule({
  declarations: [
    SearchComponent
  ],
  imports: [
    CommonModule,
    SectionLayoutModule,
    NoResultsModule,
    MatProgressBarModule,
    VideoCardModule,
    RouterModule.forChild(routes)
  ]
})
export class SearchModule { }

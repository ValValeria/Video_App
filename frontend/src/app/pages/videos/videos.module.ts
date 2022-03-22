import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";

import {VideosComponent} from './videos.component';
import {SharedModule} from "../../shared/shared.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {NoResultsModule} from "../../components/no-results/no-results.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {VideoCardModule} from "../../components/video-card/video-card.module";

const routes: Routes = [
  {path: "", pathMatch: "full", component: VideosComponent}
];

@NgModule({
  declarations: [
    VideosComponent
  ],
  imports: [
    SharedModule,
    SectionLayoutModule,
    VideoCardModule,
    RouterModule.forChild(routes),
    NoResultsModule,
    MatProgressBarModule
  ]
})
export class VideosModule {
}

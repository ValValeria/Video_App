import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";

import { VideosComponent } from './videos.component';
import {SharedModule} from "../../shared/shared.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";

const routes: Routes = [
  {path:"", pathMatch:"full", component: VideosComponent}
];

@NgModule({
  declarations: [
    VideosComponent
  ],
  imports: [
    SharedModule,
    SectionLayoutModule,
    RouterModule.forChild(routes)
  ]
})
export class VideosModule { }

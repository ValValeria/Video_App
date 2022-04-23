import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AccordionModule} from 'primeng/accordion';

import {SharedModule} from "../../shared/shared.module";
import {VideoComponent} from "./video.component";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {KnobModule} from 'primeng/knob';
import {ButtonModule} from "primeng/button";

const routes: Routes = [
  {path: "", pathMatch: "full", component: VideoComponent}
];

@NgModule({
  declarations: [
    VideoComponent
  ],
  imports: [
    SharedModule,
    SectionLayoutModule,
    RouterModule.forChild(routes),
    AccordionModule,
    KnobModule,
    ButtonModule
  ]
})
export class VideoModule {
}

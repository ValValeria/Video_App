import { NgModule } from '@angular/core';

import {MatCardModule} from "@angular/material/card";

import { VideoCardComponent } from './video-card.component';
import {CoreModule} from "../../core/core.module";

@NgModule({
  declarations: [
    VideoCardComponent
  ],
  imports: [
    CoreModule, MatCardModule
  ],
  exports: [
    VideoCardComponent
  ]
})
export class VideoCardModule { }

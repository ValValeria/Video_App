import {NgModule} from '@angular/core';

import {MatCardModule} from "@angular/material/card";

import {VideoCardComponent} from './video-card.component';
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    VideoCardComponent
  ],
  imports: [
    SharedModule, MatCardModule
  ],
  exports: [
    VideoCardComponent
  ]
})
export class VideoCardModule { }

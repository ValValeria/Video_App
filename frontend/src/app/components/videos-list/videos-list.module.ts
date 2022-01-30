import { NgModule } from '@angular/core';

import { SharedModule } from "../../shared/shared.module";
import { VideosListComponent } from "./videos-list.component";
import {VideoCardModule} from "../video-card/video-card.module";

@NgModule({
  declarations: [VideosListComponent],
  imports: [
    SharedModule,
    VideoCardModule
  ],
  exports: [VideosListComponent]
})
export class VideosListModule { }

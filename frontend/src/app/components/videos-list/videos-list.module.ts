import { NgModule } from '@angular/core';

import { CoreModule } from "../../core/core.module";
import { VideosListComponent } from "./videos-list.component";
import {VideoCardModule} from "../video-card/video-card.module";

@NgModule({
  declarations: [VideosListComponent],
  imports: [
    CoreModule,
    VideoCardModule
  ],
  exports: [VideosListComponent]
})
export class VideosListModule { }

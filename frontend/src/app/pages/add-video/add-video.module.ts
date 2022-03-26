import {NgModule} from '@angular/core';
import {SharedModule} from "../../shared/shared.module";
import {AddVideoComponent} from "./add-video.component";

@NgModule({
  declarations: [
    AddVideoComponent
  ],
  imports: [
    SharedModule
  ]
})
export class AddVideoModule {
}

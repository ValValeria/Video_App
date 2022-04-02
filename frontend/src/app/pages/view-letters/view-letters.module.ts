import {NgModule} from '@angular/core';
import {ViewLettersComponent} from './view-letters.component';
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    ViewLettersComponent
  ],
  imports: [
    SharedModule
  ]
})
export class ViewLettersModule {
}

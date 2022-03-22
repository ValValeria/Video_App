import {NgModule} from '@angular/core';
import {SharedModule} from "../../shared/shared.module";
import {NoResultsComponent} from "./no-results.component";

@NgModule({
  declarations: [
    NoResultsComponent
  ],
  imports: [
    SharedModule
  ],
  exports: [NoResultsComponent]
})
export class NoResultsModule { }

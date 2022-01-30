import {NgModule} from "@angular/core";
import {SectionLayoutComponent} from "./section-layout.component";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  imports: [SharedModule],
  declarations: [SectionLayoutComponent],
  exports: [SectionLayoutComponent]
})
export class SectionLayoutModule {
}

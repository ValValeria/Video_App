import {NgModule} from "@angular/core";
import {SectionLayoutComponent} from "./section-layout.component";
import {CoreModule} from "../../core/core.module";

@NgModule({
  imports: [CoreModule],
  declarations: [SectionLayoutComponent],
  exports: [SectionLayoutComponent]
})
export class SectionLayoutModule {
}

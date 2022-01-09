import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";

import {HomeComponent} from "./home.component";
import {CoreModule} from "../../core/core.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";

const routes: Routes = [
  {path: "", component: HomeComponent}
];

@NgModule({
  declarations: [HomeComponent],
  imports: [RouterModule.forChild(routes), CoreModule, SectionLayoutModule]
})
export class HomeModule{}

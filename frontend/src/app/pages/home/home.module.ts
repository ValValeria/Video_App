import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";

import {HomeComponent} from "./home.component";
import {SharedModule} from "../../shared/shared.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {ContactFormModule} from "../../components/contact-form/contact-form.module";

const routes: Routes = [
  {path: "", component: HomeComponent}
];

@NgModule({
  declarations: [HomeComponent],
  imports: [RouterModule.forChild(routes), SharedModule, SectionLayoutModule, ContactFormModule]
})
export class HomeModule{}

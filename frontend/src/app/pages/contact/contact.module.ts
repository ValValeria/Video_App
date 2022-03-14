import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContactComponent} from './contact.component';
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {ContactFormModule} from "../../components/contact-form/contact-form.module";
import {RouterModule, Routes} from "@angular/router";

export const routes: Routes = [
  {path: "", component: ContactComponent}
];

@NgModule({
  declarations: [
    ContactComponent
  ],
  imports: [
    CommonModule,
    SectionLayoutModule,
    ContactFormModule,
    RouterModule.forChild(routes)
  ]
})
export class ContactModule {
}

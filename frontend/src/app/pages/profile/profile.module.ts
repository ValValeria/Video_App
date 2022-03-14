import {NgModule} from '@angular/core';

import {ProfileComponent} from './profile.component';
import {SharedModule} from "../../shared/shared.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {path: "", component: ProfileComponent, pathMatch: "full"}
];

@NgModule({
  declarations: [
    ProfileComponent
  ],
  imports: [
    SharedModule,
    SectionLayoutModule,
    RouterModule.forChild(routes)
  ]
})
export class ProfileModule {
}

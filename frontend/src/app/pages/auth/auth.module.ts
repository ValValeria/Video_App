import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {MatTabsModule} from "@angular/material/tabs";

import { AuthComponent } from './auth.component';
import {SharedModule} from "../../shared/shared.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {AuthFormModule} from "../../components/auth-form/auth-form.module";

const routes: Routes = [
  {path: "", component: AuthComponent, pathMatch: "full"}
];

@NgModule({
  declarations: [
    AuthComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    SharedModule,
    SectionLayoutModule,
    MatTabsModule,
    AuthFormModule
  ]
})
export class AuthModule { }

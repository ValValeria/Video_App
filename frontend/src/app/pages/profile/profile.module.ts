import { NgModule } from '@angular/core';

import { ProfileComponent } from './profile.component';
import { SharedModule } from "../../shared/shared.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";

@NgModule({
  declarations: [
    ProfileComponent
  ],
    imports: [
        SharedModule,
        SectionLayoutModule
    ]
})
export class ProfileModule { }

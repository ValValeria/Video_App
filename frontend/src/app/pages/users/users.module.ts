import { NgModule } from '@angular/core';
import { UsersComponent } from './users.component';
import { SharedModule } from "../../shared/shared.module";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";

@NgModule({
  declarations: [
    UsersComponent
  ],
    imports: [
        SharedModule,
        SectionLayoutModule
    ]
})
export class UsersModule { }

import { NgModule } from '@angular/core';
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from '@angular/material/select';

import {SharedModule} from "../../shared/shared.module";
import {AuthFormComponent} from "./auth-form.component";

@NgModule({
  declarations: [
    AuthFormComponent
  ],
  imports: [
    SharedModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule
  ],
  exports: [AuthFormComponent]
})
export class AuthFormModule { }

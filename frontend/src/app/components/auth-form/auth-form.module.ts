import {NgModule} from '@angular/core';
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from '@angular/material/select';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {PasswordModule} from 'primeng/password';
import {InputTextModule} from 'primeng/inputtext';

import {SharedModule} from "../../shared/shared.module";
import {AuthFormComponent} from "./auth-form.component";
import {ButtonModule} from 'primeng/button';

@NgModule({
  declarations: [
    AuthFormComponent
  ],
  imports: [
    SharedModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatSnackBarModule,
    PasswordModule,
    InputTextModule,
    ButtonModule
  ],
  exports: [AuthFormComponent]
})
export class AuthFormModule {
}

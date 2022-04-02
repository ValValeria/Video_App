import {NgModule} from '@angular/core';
import {MatSnackBarModule} from "@angular/material/snack-bar";

import {SharedModule} from "../../shared/shared.module";
import {ContactFormComponent} from "./contact-form.component";
import {ButtonModule} from "primeng/button";
import {InputTextModule} from 'primeng/inputtext';
import {InputTextareaModule} from 'primeng/inputtextarea';

@NgModule({
  declarations: [
    ContactFormComponent
  ],
  imports: [
    SharedModule,
    MatSnackBarModule,
    InputTextModule,
    ButtonModule,
    InputTextareaModule
  ],
  exports: [ContactFormComponent]
})
export class ContactFormModule { }

import {NgModule} from '@angular/core';
import {MatSnackBarModule} from "@angular/material/snack-bar";

import {SharedModule} from "../../shared/shared.module";
import {ContactFormComponent} from "./contact-form.component";
import {InputTextModule} from 'primeng/inputtext';
import {ToastModule} from 'primeng/toast';

@NgModule({
  declarations: [
    ContactFormComponent
  ],
  imports: [
    SharedModule,
    MatSnackBarModule,
    InputTextModule,
    ToastModule
  ],
  exports: [ContactFormComponent]
})
export class ContactFormModule { }

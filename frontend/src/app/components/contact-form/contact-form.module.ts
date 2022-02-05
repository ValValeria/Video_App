import { NgModule } from '@angular/core';
import { MatSnackBarModule } from "@angular/material/snack-bar";

import { SharedModule } from "../../shared/shared.module";
import { ContactFormComponent } from "./contact-form.component";

@NgModule({
  declarations: [
    ContactFormComponent
  ],
  imports: [
    SharedModule,
    MatSnackBarModule
  ],
  exports: [ContactFormComponent]
})
export class ContactFormModule { }

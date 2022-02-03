import { NgModule } from '@angular/core';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from '@angular/material/select';

import { SharedModule } from "../../shared/shared.module";
import { ContactFormComponent } from "./contact-form.component";

@NgModule({
  declarations: [
    ContactFormComponent
  ],
  imports: [
    SharedModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule
  ],
  exports: [ContactFormComponent]
})
export class ContactFormModule { }

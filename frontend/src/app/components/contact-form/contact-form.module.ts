import { NgModule } from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

import { SharedModule } from "../../shared/shared.module";
import {ContactFormComponent} from "./contact-form.component";

@NgModule({
  declarations: [
    ContactFormComponent
  ],
  imports: [
    SharedModule,
    MatFormFieldModule,
    MatInputModule
  ],
  exports: [ContactFormComponent]
})
export class ContactFormModule { }

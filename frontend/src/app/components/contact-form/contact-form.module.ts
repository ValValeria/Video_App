import { NgModule } from '@angular/core';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from "@angular/material/snack-bar";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

import { SharedModule } from "../../shared/shared.module";
import { ContactFormComponent } from "./contact-form.component";

@NgModule({
  declarations: [
    ContactFormComponent
  ],
  imports: [
    SharedModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    FormsModule
  ],
  exports: [ContactFormComponent]
})
export class ContactFormModule { }

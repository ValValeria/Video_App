import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from "@angular/material/button";
import { MatListModule } from '@angular/material/list';
import { HttpClientModule } from "@angular/common/http";
import {MatCardModule} from "@angular/material/card";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatNativeDateModule} from "@angular/material/core";

const modules: any[] = [
  CommonModule, MatButtonModule, FormsModule, MatListModule,
  MatCardModule, ReactiveFormsModule, MatFormFieldModule,
  MatInputModule, FormsModule, HttpClientModule,
  MatNativeDateModule
];

const services: any[] = [];

@NgModule({
  providers: [
    ...services
  ],
  exports: [...modules]
})
export class SharedModule { }

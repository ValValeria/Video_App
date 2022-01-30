import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from "@angular/material/button";
import { MatListModule } from '@angular/material/list';
import { HttpClientModule } from "@angular/common/http";
import {MatCardModule} from "@angular/material/card";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

const modules: any[] = [CommonModule,
                        MatButtonModule,
                        FormsModule,
                        MatListModule,
                        MatCardModule,
                        ReactiveFormsModule,
                        HttpClientModule];

const services: any[] = [];

@NgModule({
  providers: [
    ...services
  ],
  exports: [...modules]
})
export class SharedModule { }

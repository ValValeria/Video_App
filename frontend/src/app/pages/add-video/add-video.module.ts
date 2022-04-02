import {NgModule} from '@angular/core';
import {SharedModule} from "../../shared/shared.module";
import {AddVideoComponent} from "./add-video.component";
import {SectionLayoutModule} from "../../layouts/section-layout/section-layout.module";
import {RouterModule, Routes} from "@angular/router";
import {EditorModule} from 'primeng/editor';
import {ButtonModule} from 'primeng/button';
import {TabViewModule} from 'primeng/tabview';
import {StepsModule} from 'primeng/steps';

export const routes: Routes = [
  {path: "", component: AddVideoComponent}
];

@NgModule({
  declarations: [
    AddVideoComponent
  ],
  imports: [
    SharedModule,
    SectionLayoutModule,
    EditorModule,
    TabViewModule,
    RouterModule.forChild(routes),
    ButtonModule,
    StepsModule
  ]
})
export class AddVideoModule {
}

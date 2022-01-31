import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  {path: '',
   loadChildren: () => import("./pages/home/home.module").then(v => v.HomeModule),
   pathMatch: 'full'
  },
  {path: 'auth',
   loadChildren: () => import("./pages/auth/auth.module").then(v => v.AuthModule),
   pathMatch: 'full'
  },
  {
   path: 'videos',
   loadChildren: () => import("./pages/videos/videos.module").then(v => v.VideosModule),
   canLoad: [AuthGuard]
  }
];

@NgModule({
  providers: [AuthGuard],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {AuthGuard} from "./guards/auth.guard";
import {AdminOnlyGuard} from "./guards/admin-only.guard";

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
   loadChildren: () => import("./pages/videos/videos.module").then(v => v.VideosModule)
  },
  {
   path: 'profile/:id',
   loadChildren: () => import("./pages/profile/profile.module").then(v => v.ProfileModule),
   canLoad: [AuthGuard]
  },
  {
    path: 'users',
    loadChildren: () => import("./pages/users/users.module").then(v => v.UsersModule),
    canLoad: [AuthGuard]
  },
  {
    path: 'contacts',
    loadChildren: () => import("./pages/contact/contact.module").then(v => v.ContactModule)
  },
  {
    path: 'admin-panel',
    loadChildren: () => import("./pages/admin-panel/admin-panel.module").then(v => v.AdminPanelModule),
    canLoad: [AdminOnlyGuard]
  },
  {
    path: 'videos/:id',
    loadChildren: () => import("./pages/video/video.module").then(v => v.VideoModule),
    canLoad: [AuthGuard]
  },
  {
    path: 'search',
    loadChildren: () => import("./pages/search/search.module").then(v => v.SearchModule),
    canLoad: [AuthGuard]
  }
];

@NgModule({
  providers: [AuthGuard, AdminOnlyGuard],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

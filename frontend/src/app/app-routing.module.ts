import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AuthGuard} from "./guards/auth.guard";
import {AdminOnlyGuard} from "./guards/admin-only.guard";
import {ProfileGuard} from "./guards/profile.guard";
import {OnlyAnonimGuard} from "./guards/only-anonim.guard";

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import("./pages/home/home.module").then(v => v.HomeModule),
    pathMatch: 'full'
  },
  {
    path: 'auth',
    loadChildren: () => import("./pages/auth/auth.module").then(v => v.AuthModule),
    pathMatch: 'full',
    canLoad: [OnlyAnonimGuard]
  },
  {
    path: 'videos',
    loadChildren: () => import("./pages/videos/videos.module").then(v => v.VideosModule),
    pathMatch: "full"
  },
  {
    path: 'profile/:id',
    loadChildren: () => import("./pages/profile/profile.module").then(v => v.ProfileModule),
    pathMatch: "full",
    canLoad: [ProfileGuard, AuthGuard]
  },
  {
    path: 'users',
    loadChildren: () => import("./pages/users/users.module").then(v => v.UsersModule),
    canLoad: [AuthGuard],
    pathMatch: "full"
  },
  {
    path: 'contacts',
    loadChildren: () => import("./pages/contact/contact.module").then(v => v.ContactModule)
  },
  {
    path: 'video/:id',
    loadChildren: () => import("./pages/video/video.module").then(v => v.VideoModule),
    pathMatch: "full",
    canLoad: [AuthGuard]
  },
  {
    path: 'search',
    loadChildren: () => import("./pages/search/search.module").then(v => v.SearchModule),
    canLoad: [AuthGuard],
    pathMatch: "full"
  },
  {
    path: 'add-video',
    loadChildren: () => import("./pages/add-video/add-video.module").then(v => v.AddVideoModule),
    canLoad: [AuthGuard, AdminOnlyGuard]
  },
  {
    path: 'letters',
    loadChildren: () => import("./pages/view-letters/view-letters.module").then(v => v.ViewLettersModule),
    canLoad: [AuthGuard, AdminOnlyGuard]
  }
];

@NgModule({
  providers: [AuthGuard, AdminOnlyGuard],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

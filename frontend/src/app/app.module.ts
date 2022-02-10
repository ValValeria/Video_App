import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSidenavModule } from '@angular/material/sidenav';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderModule } from "./components/header/header.module";
import { FooterModule } from "./components/footer/footer.module";
import { SharedModule } from "./shared/shared.module";
import {NavigationDrawerModule} from "./components/navigation-drawer/navigation-drawer.module";
import {UserService} from "./services/user.service";
import { VideoComponent } from './pages/video/video.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { SearchComponent } from './pages/search/search.component';

@NgModule({
  declarations: [
    AppComponent,
    VideoComponent,
    UserProfileComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    HeaderModule,
    FooterModule,
    NavigationDrawerModule
  ],
  providers: [
    {provide: UserService, useClass: UserService}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

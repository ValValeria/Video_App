import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {auditTime} from "rxjs";
import {MatDrawer} from "@angular/material/sidenav";
import {menuClick$} from "./subjects/header-component.subject";
import {ISingleResultResponse, IUser} from "./types/interfaces";
import {HttpClient} from "@angular/common/http";
import {retry} from "rxjs/operators";
import {UserService} from "./services/user.service";
import {authFormSubject$} from "./subjects/auth-form.subject";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  @ViewChild("drawer", {read: MatDrawer})
  public matDrawer: MatDrawer | undefined;

  private readonly httpClient: HttpClient;
  private readonly userService: UserService;

  public constructor(
    httpClient: HttpClient,
    userService: UserService
  ) {
    this.httpClient = httpClient;
    this.userService = userService;
  }

  ngOnInit(): void {
    menuClick$
      .pipe(auditTime(100))
      .subscribe(async () => {
        if (this.matDrawer !== undefined) {
          await this.matDrawer.toggle();
        }
      });
  }

  ngAfterViewInit(): void {
    try {
      const user: IUser = JSON.parse(localStorage.getItem("auth") ?? '{}');

      if (user !== null && user.id && user.username) {
        this.httpClient
          .post<ISingleResultResponse<any>>(`/api/auth/login`, {
            id: user.id,
            username: user.username,
            password: user.password
          })
          .pipe(
            retry(3)
          )
          .subscribe(v => {
            if (v.status == "ok") {
              this.userService.login(user);
            }

            authFormSubject$.next();
          });
      }
    } catch (e) {
      console.error(e)
    }
  }
}

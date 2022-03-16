import {Component} from '@angular/core';

import {IMultipleResponse, IResponseType, IUser} from "../../types/interfaces";
import {User} from "../../models/user.model";
import {UserService} from "../../services/user.service";
import {HttpClient} from "@angular/common/http";
import {retry} from "rxjs/operators";
import {ActivatedRoute, Router} from "@angular/router";
import {Roles} from "../../types/roles";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  public totalVideoCount: number = 0;
  public user: IUser = new User();

  constructor(
    private userService: UserService,
    private httpClient: HttpClient,
    private activatedRoute: ActivatedRoute,
    private matSnackBar: MatSnackBar,
    private router: Router
  ) {
    activatedRoute.paramMap.subscribe(v => {
       const id: number = parseInt(v.get('id') as string, 10);

       if (userService.user.id === id) {
         this.user = userService.user;

         this.loadVideosCount();
       } else if (userService.user.role === Roles.ADMIN) {
         this.loadUserById(id);
       }
    });
  }

  public deleteUserProfile(): void {
    this.httpClient
      .delete<IResponseType<any>>(`/api/profile/${this.user.id}`)
      .subscribe(v => {
        if(v.status === "ok") {
           this.matSnackBar.open("The profile is deleted", "Close");

           localStorage.removeItem("auth");

           setTimeout(async () => {
             await this.router.navigateByUrl("/");
           }, 900);
        }
      });
  }

  private loadVideosCount(): void {
    this.httpClient
      .get<IResponseType<{results: number}>>(`/api/profile/${this.user.id}/get-videos-count`)
      .pipe(
        retry(3)
      )
      .subscribe(v => {
        this.totalVideoCount = v.data.results;
      });
  }

  private loadUserById(userId: number): void {
     this.httpClient
       .get<IMultipleResponse<IUser>>(`/api/videos/${userId}/`)
       .subscribe(v => {
         this.user = v.results[0];

         this.loadVideosCount();
       });
  }

  public async videosByUser(): Promise<void> {
    await this.router.navigateByUrl(`/videos/${this.user.id}`);
  }

  public changeUserInfo(): void {
    throw new Error();
  }
}

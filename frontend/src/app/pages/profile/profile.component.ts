import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

import {Observable} from "rxjs";
import {catchError, retry} from "rxjs/operators";

import {IMultipleResponse, IResponseType, ISingleResultResponse, IUser} from "../../types/interfaces";
import {UserModel} from "../../models/user.model";
import {UserService} from "../../services/user.service";
import {Roles} from "../../types/roles";
import {EmptyResponse} from "../../types/classes";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  public totalVideoCount: number = 0;
  public user: IUser = new UserModel();
  public showEditControls: boolean = false;
  public hideActions: boolean = true;

  public readonly form: FormGroup;

  constructor(
    public userService: UserService,
    private httpClient: HttpClient,
    private activatedRoute: ActivatedRoute,
    private matSnackBar: MatSnackBar,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    activatedRoute.paramMap.subscribe(v => {
       const id: number = parseInt(v.get('id') as string, 10);

       if (userService.user.id === id) {
         this.user = userService.user;
         this.hideActions = false;
         this.loadVideosCount();
       } else if (userService.isAdmin()) {
         this.hideActions = false;
         this.loadUserById(id);
       } else {
         this.hideActions = true;
         this.loadUserWithHideProps(id);
       }
    });

    this.form = formBuilder.group({
      username: ["", Validators.compose([
        Validators.min(10),
        Validators.max(25),
        Validators.required
      ])
      ],
      password: ["", Validators.compose([
        Validators.min(10),
        Validators.max(20),
        Validators.required
      ])],
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
       .get<ISingleResultResponse<IUser>>(`/api/user/${userId}/`)
       .subscribe(v => {
         this.user = v.data.result;

         this.loadVideosCount();
       });
  }

  public async videosByUser(): Promise<void> {
    await this.router.navigate(['videos'], {
      queryParams: {userId: this.user.id}
    });
  }

  public changeUserInfo(): void {
    this.showEditControls = true;
  }

  public addVideo(): void {

  }

  public saveUserChanges(): void {
    if (this.form.valid) {
      this.user.username = this.form.get("username")?.value;
      this.user.password = this.form.get("password")?.value;

      this.httpClient
        .post<IResponseType<any>>(`/api/profile/${this.user.id}`, this.user)
        .pipe(
          catchError(v =>Observable.create(new EmptyResponse()))
        )
        .subscribe(v => {
          if ((v as IResponseType<any>).status === "ok") {
             this.showEditControls = false;
           } else {
             this.matSnackBar.open("Some http errors have occurred :(", "Close");
           }
        });
    }
  }

  public loadUserWithHideProps(id: number): void {
    this.httpClient
      .get<ISingleResultResponse<IUser>>(`/api/public-user/${id}`)
      .subscribe(v => {
        this.user = v.data.result;

        this.loadVideosCount();
      });
  }
}

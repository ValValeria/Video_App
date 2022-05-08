import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {ErrorStateMatcher} from "@angular/material/core";

import {UserService} from "../../services/user.service";
import {IResponseType, IUser} from "../../types/interfaces";
import {UserModel} from "../../models/user.model";
import {Roles} from "../../types/roles";
import {authFormSubject$} from "../../subjects/auth-form.subject";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.scss'],
})
export class AuthFormComponent implements OnChanges {
  @Input()
  public isLogin: boolean = true;

  public readonly form: FormGroup;
  public readonly matcher: MyErrorStateMatcher;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.form = formBuilder.group({
      password: ['', Validators.compose([
          Validators.required,
          Validators.max(25),
          Validators.min(10)
        ]
      )],
      username: ['', Validators.compose(
        [
          Validators.required,
          Validators.max(25),
          Validators.min(10),
          Validators.pattern(/[aA-zZ]/gi)
        ]
      )],
      email: ['']
    });

    this.matcher = new MyErrorStateMatcher();
  }

  ngOnChanges(changes: SimpleChanges): void {
    let value = changes['isLogin'].currentValue;
    const validators = [
      Validators.email,
      Validators.required,
      Validators.max(25),
      Validators.min(10)
    ];

    if (value === true) {
      this.email.removeValidators(validators);
    } else if (!this.form.contains("email")) {
      this.email.addValidators(validators);
    }

    this.email.updateValueAndValidity();
  }

  async submit(): Promise<void> {
    if (this.form.valid) {
      const url: string = this.isLogin ? '/api/auth/login' : '/api/auth/sign-up';

      this.httpClient.post<IResponseType<{id: number, role: Roles}>>(url, this.form.value).subscribe(v => {
        if (v.status === "ok") {
          const user: IUser = new UserModel();

          user.id = v.data.id;
          user.username = this.username.value;
          user.password = this.password.value;
          user.role = v.data.role ?? Roles.USER;

          this.userService.login(this.form.value as IUser);

          authFormSubject$.next();

          this.router.navigateByUrl("/");

          localStorage.setItem("auth", JSON.stringify({
            id: user.id,
            username: user.username,
            password: user.password
          }));
        } else {
          if (this.isLogin) {
            this.snackBar.open("No user with such username is in our database", "Close");
          } else {
            this.snackBar.open("User with such username is already in our database", "Close");
          }
        }
      });
    } else {
      this.snackBar.open("Please, check the validity of form", "Close");
    }
  }

  public get email(): FormControl {
    return this.form.get("email") as FormControl;
  }

  public get password(): FormControl {
    return this.form.get("password") as FormControl;
  }

  public get username(): FormControl {
    return this.form.get("username") as FormControl;
  }
}

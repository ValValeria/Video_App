import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {ErrorStateMatcher} from "@angular/material/core";

import {UserService} from "../../services/user.service";
import {IResponseType, ITokens} from "../../types/interfaces";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.scss']
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
      )]
    });

    this.matcher = new MyErrorStateMatcher();
  }

  ngOnChanges(changes: SimpleChanges): void {
    let value = changes['isLogin'].currentValue;

    if (value === true) {
      this.form.removeControl("email");
    } else if (!this.form.contains("email")) {
      const control = this.formBuilder.control(
        "",
        Validators.compose([
          Validators.email,
          Validators.required,
          Validators.max(25),
          Validators.min(10)
        ])
      );
      this.form.addControl("email", control);
    }
  }

  async submit(): Promise<void> {
    if (this.form.valid) {
      this.httpClient.post<IResponseType<ITokens>>('/api/auth/sign-up', this.form.value).subscribe(v => {
        if (v.status === "ok") {
          this.userService.user.isAuth = true;

          this.router.navigateByUrl("/");
        } else {
          this.snackBar.open("Some errors have occurred", "Close");
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

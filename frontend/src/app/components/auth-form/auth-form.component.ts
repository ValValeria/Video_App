import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/user.service";
import {IResponseType, ITokens} from "../../types/interfaces";

@Component({
  selector: 'app-auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.scss']
})
export class AuthFormComponent implements OnChanges {
  @Input() isLogin: boolean = true;
  public form: FormGroup;
  private readonly validators: ((control: AbstractControl) => (ValidationErrors | null))[];

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private userService: UserService
  ) {
    this.validators = [Validators.required, Validators.max(25), Validators.min(10)];

    this.form = formBuilder.group({
        password: ['', Validators.compose(this.validators)],
        username: ['', Validators.compose(this.validators)]
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    let value = changes['isLogin'].currentValue;

    if (value === true) {
      this.form.removeControl("email");
    } else if(!this.form.contains("email")) {
      const control = this.formBuilder.control("", Validators.compose([...this.validators, Validators.email]))
      this.form.addControl("email", control);
    }
  }

  async submit(): Promise<void> {
    if(this.form.valid) {
      this.httpClient.post<IResponseType<ITokens>>('/api/signup', this.form.value).subscribe(v => {
        const user = this.userService.user;

        if (v.status === "ok") {
          const refreshToken = v.data.refreshToken;
          const accessToken = v.data.accessToken;

          this.userService.refreshToken = refreshToken;
          this.userService.accessToken = accessToken;
        }
      });
    }
  }
}
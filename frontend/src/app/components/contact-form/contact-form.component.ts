import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.scss']
})
export class ContactFormComponent {
  public formGroup: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private snackBar: MatSnackBar
  ) {
    this.formGroup = formBuilder.group({
      email: ["", Validators.compose(
        [
          Validators.max(40),
          Validators.min(10),
          Validators.email,
          Validators.required
        ]
      )],
      message: ["", Validators.compose(
        [
          Validators.required,
          Validators.max(400),
          Validators.min(10)
        ]
      )]
    });
  }

  public submit($event: Event): void {
    $event.stopPropagation();

    if (this.formGroup.valid) {
      this.httpClient.post("/api/letters", this.formGroup.value)
        .subscribe(v => {
           this.snackBar.open("Your message has been sent", "Close");
        });
    }
  }
}

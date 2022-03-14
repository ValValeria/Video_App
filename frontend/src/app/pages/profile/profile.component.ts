import {
  Component,
  OnInit
} from '@angular/core';

import {IUser} from "../../types/interfaces";
import {User} from "../../models/user.model";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public readonly user: IUser;

  constructor() {
    this.user = new User();
  }

  ngOnInit(): void {
    this.user.id = 10;
    this.user.password = 'dkdkkdkd';
    this.user.username = 'kdkdddjdj';
  }
}

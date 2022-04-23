import {Injectable} from '@angular/core';

import {IUser} from "../types/interfaces";
import {UserModel} from "../models/user.model";
import {Roles} from "../types/roles";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly _user: IUser;

  constructor() {
    this._user = new UserModel();
  }

  get user(): IUser {
    return this._user;
  }

  public isAdmin(): boolean {
    return this._user.role === Roles.ADMIN;
  }
}

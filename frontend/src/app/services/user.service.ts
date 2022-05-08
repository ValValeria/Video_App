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

  public login(user: IUser): void {
    this._user.isAuth = true;
    this._user.role = user.role;
    this._user.username = user.username;
    this._user.password = user.password;
    this._user.id = user.id;
  }
}

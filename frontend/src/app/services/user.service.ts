import { Injectable } from '@angular/core';
import {IUser} from "../types/interfaces";
import {User} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly _user: IUser;

  constructor() {
    this._user = new User();
  }

  get user(): IUser {
    return this._user;
  }
}

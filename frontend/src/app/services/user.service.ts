import { Injectable } from '@angular/core';

import {IUser} from "../types/interfaces";
import {User} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly _user: IUser;
  private _refreshToken: string;
  private _accessToken: string;

  constructor() {
    this._user = new User();

    this._accessToken = "";
    this._refreshToken = "";
  }

  get user(): IUser {
    return this._user;
  }

  get refreshToken(): string {
    return this._refreshToken;
  }

  set refreshToken(value: string) {
    this._refreshToken = value;
  }

  get accessToken(): string {
    return this._accessToken;
  }

  set accessToken(value: string) {
    this._accessToken = value;
  }
}

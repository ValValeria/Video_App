import {IUser} from "../types/interfaces";
import {Roles} from "../types/roles";

export class User implements IUser {
  private _id: number;
  private _isAuth: boolean;
  private _password: string;
  private _username: string;
  private _role: Roles;

  public constructor() {
    this._id = 0;
    this._isAuth = false;
    this._password = '';
    this._username = '';
    this._role = Roles.ADMIN;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get isAuth(): boolean {
    return this._isAuth;
  }

  set isAuth(value: boolean) {
    this._isAuth = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get role(): Roles {
    return this._role;
  }

  set role(value: Roles) {
    this._role = value;
  }
}

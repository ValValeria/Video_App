import {IUser, IVideo} from "../types/interfaces";
import {UserModel} from "./user.model";

export class VideoModel implements IVideo{
  private _createdAt: string;
  private _description: string;
  private _id: number;
  private _path: string;
  private _title: string;
  private _author: IUser;

  public constructor() {
    this._createdAt = new Date().toLocaleDateString();
    this._description = "";
    this._id = -1;
    this._path = '';
    this._title = '';
    this._author = new UserModel();
  }

  get createdAt(): string {
    return this._createdAt;
  }

  set createdAt(value: string) {
    this._createdAt = value;
  }

  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get path(): string {
    return this._path;
  }

  set path(value: string) {
    this._path = value;
  }

  get title(): string {
    return this._title;
  }

  set title(value: string) {
    this._title = value;
  }

  get author(): IUser {
    return this._author;
  }

  set author(value: IUser) {
    this._author = value;
  }
}

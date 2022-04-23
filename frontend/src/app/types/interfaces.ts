import {Roles} from "./roles";

export interface IVideo {
  id: number;
  path: string;
  title: string;
  createdAt: string;
  description: string;
  author: IUser;
}

export interface IResponseType<T> {
  errors: string[];
  status: "ok" | "not ok";
  data: T;
}

export interface ITokens{
  refreshToken: string;
  accessToken: string;
}

export interface IMultipleResponse<T> {
  results: T[];
}

export interface ISingleResultResponse<T> {
  errors: string[];
  status: "ok" | "not ok";
  data: {
    result: T
  }
}

export interface IUser{
  isAuth: boolean;
  username: string;
  password?: string;
  id: number;
  role?: Roles;
}

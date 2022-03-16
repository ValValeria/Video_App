import {IResponseType} from "./interfaces";

export class EmptyResponse implements IResponseType<any> {
  data: any;
  errors: string[];
  status: "ok" | "not ok";

  public constructor() {
    this.errors = [];
    this.status = "not ok";
  }
}

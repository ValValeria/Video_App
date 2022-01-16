export interface IVideo {
  id: number;
  path: string;
  title: string;
  createdAt: string;
  description: string
}

export interface IResponseType<T> {
  errors: string[];
  status: "ok" | "not ok";
  data: T;
}

export interface IMultipleResponse<T> {
  results: T[];
}

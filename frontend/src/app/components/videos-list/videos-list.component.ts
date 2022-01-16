import { Component, OnInit } from '@angular/core';

import { HttpClient } from "@angular/common/http";

import { IMultipleResponse, IResponseType, IVideo } from "../../types/interfaces";

@Component({
  selector: 'app-videos-list',
  templateUrl: './videos-list.component.html',
  styleUrls: ['./videos-list.component.scss']
})
export class VideosListComponent implements OnInit {
  public readonly videos: IVideo[];

  constructor(private httpClient: HttpClient) {
    this.videos = [];
  }

  ngOnInit(): void {
    this.httpClient.get<IResponseType<IMultipleResponse<IVideo>>>("/api/videos?page=1&size=2")
      .subscribe(v => {
        this.videos.push(...v.data.results);
      });
  }
}

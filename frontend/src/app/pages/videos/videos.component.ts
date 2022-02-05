import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {IMultipleResponse, IResponseType, IVideo} from "../../types/interfaces";

@Component({
  selector: 'app-videos',
  templateUrl: './videos.component.html',
  styleUrls: ['./videos.component.scss']
})
export class VideosComponent implements OnInit {
  public page: number = 1;
  public size: number = 4;

  public readonly videos: IVideo[];

  constructor(private httpClient: HttpClient) {
    this.videos = [];
  }

  ngOnInit(): void {
    this.loadVideos();
  }

  private loadVideos(): void {
    this.httpClient.get<IResponseType<IMultipleResponse<IVideo>>>(`/api/videos?page=${this.page}&size=${this.size}&sort=id`)
      .subscribe(v => {
        this.videos.splice(0, this.videos.length, ...v.data.results);
      });
  }
}

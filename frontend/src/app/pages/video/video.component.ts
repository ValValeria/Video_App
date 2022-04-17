import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";

import {IMultipleResponse, ISingleResultResponse, IVideo} from "../../types/interfaces";
import {VideoModel} from "../../models/video.model";

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.scss']
})
export class VideoComponent {
  private _video: IVideo;
  private id: string;

  constructor(
    private httpClient: HttpClient,
    private route: ActivatedRoute
  ) {
    this._video = new VideoModel();
    this.id = "0";

    this.route.paramMap.subscribe(v => {
      this.id = v.get("id") as string;

      this.loadData();
    });
  }

  private loadData(): void {
    this.httpClient.get<ISingleResultResponse<IVideo>>(`/api/videos/${this.id}`)
      .subscribe(v => {
        this._video = v.data.result;
      });
  }

  public get video(): IVideo {
    return this._video;
  }
}

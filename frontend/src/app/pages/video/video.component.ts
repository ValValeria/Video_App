import {Component} from '@angular/core';
import {HttpClient, HttpEventType, HttpRequest} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

import {ISingleResultResponse, IVideo} from "../../types/interfaces";
import {VideoModel} from "../../models/video.model";

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.scss']
})
export class VideoComponent {
  private _video: IVideo;
  private id: string;
  public isLoading: boolean;
  public value: number;

  constructor(
    private httpClient: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this._video = new VideoModel();
    this.id = "0";
    this.isLoading = true;
    this.value = 0;

    this.route.paramMap.subscribe(v => {
      this.id = v.get("id") as string;

      this.loadData();
    });
  }

  private loadData(): void {
    this.isLoading = true;
    this.value = 0;

    const req = new HttpRequest<ISingleResultResponse<IVideo>>("GET", `/api/videos/${this.id}`, {
      reportProgress: true
    });

    this.httpClient.request(req)
      .pipe()
      .subscribe(v => {
          if (v.type === HttpEventType.Sent) {
            this.value = 70;
          }
          if (v.type === HttpEventType.Response) {
            const body = v.body as ISingleResultResponse<IVideo>;
            this._video = body.data.result;
            this.isLoading = false;
            this.value = 0;
          }
      });
  }

  public get video(): IVideo {
    return this._video;
  }

  public async navigateToAuthorPage(): Promise<void> {
    await this.router.navigate(['profile', this._video.author.id]);
  }
}

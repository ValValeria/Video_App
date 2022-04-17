import {Component, Input, OnInit} from '@angular/core';
import {IVideo} from "../../types/interfaces";
import {VideoModel} from "../../models/video.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.scss']
})
export class VideoCardComponent {
  private _video: IVideo | undefined;

  public constructor(private router: Router) {
  }

  @Input()
  public set video(value: IVideo) {
     if(value != null) {
       this._video = value;
     }
  }

  public get video(): IVideo {
    let result =  this._video;

    if (result == null) {
        result = new VideoModel();
    }

    return result;
  }

  public async handleClick(): Promise<void> {
    await this.router.navigateByUrl(`/video/${this.video.id}`);
  }
}

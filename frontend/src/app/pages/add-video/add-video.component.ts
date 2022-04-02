import {Component, OnInit} from '@angular/core';
import {IVideo} from "../../types/interfaces";
import {VideoModel} from "../../models/video.model";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-add-video',
  templateUrl: './add-video.component.html',
  styleUrls: ['./add-video.component.scss']
})
export class AddVideoComponent implements OnInit {
  public readonly video: IVideo;
  public readonly items: MenuItem[];

  constructor() {
    this.video = new VideoModel();
    this.items = [
      {label: "Upload an video"},
      {label: "Add description"},
      {label: "Save the video"},
    ];
  }

  ngOnInit(): void {
  }
}

import {Component, Input, OnInit} from '@angular/core';
import {IVideo} from "../../types/interfaces";

@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.scss']
})
export class VideoCardComponent {
  @Input() video: IVideo | null = null;
}

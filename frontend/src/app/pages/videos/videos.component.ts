import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {IMultipleResponse, IResponseType, IVideo} from "../../types/interfaces";
import {catchError, of} from "rxjs";

@Component({
  selector: 'app-videos',
  templateUrl: './videos.component.html',
  styleUrls: ['./videos.component.scss']
})
export class VideosComponent implements OnInit {
  public page: number = 0;
  public size: number = 4;
  public isError: boolean = false;
  public isLoading: boolean = true;

  public readonly videos: IVideo[];

  constructor(private httpClient: HttpClient) {
    this.videos = [];
  }

  ngOnInit(): void {
    this.loadVideos();
  }

  private loadVideos(): void {
    this.isLoading = true;

    this.httpClient
      .get<IResponseType<IMultipleResponse<IVideo>>>(`/api/videos?page=${this.page}&size=${this.size}&sort=id`)
      .pipe(
        catchError(() => {
          this.isError = true;

          return of(null);
        })
      )
      .subscribe(v => {
        this.isLoading = false;

        if (v !== null) {
          this.isError = false;

          this.videos.splice(0, this.videos.length, ...v.data.results);
        }
      });
  }
}

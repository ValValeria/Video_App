import { Component, OnInit } from '@angular/core';
import {IMultipleResponse, IResponseType, IVideo} from "../../types/interfaces";
import {HttpClient} from "@angular/common/http";
import {catchError, of} from "rxjs";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  public page: number = 0;
  public size: number = 4;
  public isError: boolean = false;
  public isLoading: boolean = true;
  public search: string = '';

  public readonly videos: IVideo[];

  constructor(private httpClient: HttpClient) {
    this.videos = [];
  }

  ngOnInit(): void {
    this.loadVideos();
  }

  private loadVideos(): void {
    this.isLoading = true;
    this.search = encodeURIComponent(this.search)

    this.httpClient
      .get<IResponseType<IMultipleResponse<IVideo>>>(
        `/api/auth/search/?page=${this.page}&per_page=${this.size}&search=${this.search}`
      )
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

          this.videos.push(...v.data.results);
        }
      });
  }
}

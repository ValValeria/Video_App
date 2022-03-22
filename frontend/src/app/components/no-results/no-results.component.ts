import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-no-results',
  templateUrl: './no-results.component.html',
  styleUrls: ['./no-results.component.scss']
})
export class NoResultsComponent implements OnInit {
  @Input()
  public isError: boolean = false;
  @Input()
  public noResults: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }
}

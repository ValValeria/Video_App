import {Component, Input} from "@angular/core";

@Component({
  selector: 'app-section-layout',
  templateUrl: './section-layout.component.html',
  styleUrls: ['./section-layout.component.scss']
})
export class SectionLayoutComponent {
  @Input() cssClass: string = 'app';
  @Input() title: string = '';
  @Input() description: string = '';
}

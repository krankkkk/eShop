import {Component, ContentChildren, Input, QueryList, AfterContentInit} from '@angular/core';

@Component({
  selector: 'app-tab',
  template: `<div *ngIf='active' class='tab-content'>
                 <ng-content></ng-content>
             </div>`
})
export class TabComponent {
  active: boolean;
  @Input() title = '';
  constructor() {
    this.active = false;
  }
}

@Component({
  selector: 'app-tabs',
  styleUrls: ['tabs.component.css'],
  templateUrl: 'tabs.component.html'})
export class TabsComponent {

  // injizieren allter <ch-tab> Tags die innerhalb der Componente benutzt wurden

  @ContentChildren(TabComponent) tabs!: QueryList<TabComponent>;

  /*
  ngAfterContentInit() {
    this.tabs.first.active = true;
  }
  */

  activate(component: TabComponent) {
    for (const tab of this.tabs) {
      tab.active = false;
    }
    component.active = true;
  }
}

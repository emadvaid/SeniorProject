import { Component, OnInit } from '@angular/core';
import { LanguageKey} from '../../models/LanguageKey';

@Component({
  selector: 'app-key-view',
  templateUrl: './key-view.component.html',
  styleUrls: ['./key-view.component.css']
})
export class KeyViewComponent implements OnInit {

  keys = [];
  sortedKeys = [];
  versionLanguageList = [];
  version = [];


  criteria = '';

  constructor() { }

  ngOnInit() {
  }

}

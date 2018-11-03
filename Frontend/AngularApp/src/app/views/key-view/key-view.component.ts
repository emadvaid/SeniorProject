import { Component, OnInit } from '@angular/core';
import { LanguageKey} from '../../models/LanguageKey';
import {VersionService} from '../../services/versions/versions.service';
import {LanguagesService} from '../../services/languages/languages.service';
import { Version } from 'src/app/models/Version';
import { Language } from 'src/app/models/Language';


@Component({
  selector: 'app-key-view',
  templateUrl: './key-view.component.html',
  styleUrls: ['./key-view.component.css']
})
export class KeyViewComponent implements OnInit {

  keys = [];
  sortedKeys = [];
  versionLanguageList = [];
  model: any = {};
  languages: any = {};

  //current Data
  currVersion = '';
  currLanguage = '';


  criteria = '';

  constructor(
    private versionService: VersionService,
    private languageService: LanguagesService
  ) { }

  ngOnInit() {
    this.getVersions();
    this.getLanguages();

  }

  async getVersions() {
    const versions = await this.versionService.getAll().toPromise();
    this.model.versions = versions;
      this.currVersion = this.model.versions[0].verNum;
      console.log(versions);
  }

  async getLanguages() {
    const languages = await this.languageService.getAll().toPromise();
      this.languages.lang = languages;
      console.log(languages);
      this.currLanguage = this.languages.lang[0].langCode;
      console.log(this.currLanguage);

  }

  setLangCode() {
    console.log(this.currLanguage);
  }

  getKeyList(){
    const tableName = this.currLanguage + '_' + this.currVersion;
  }

}

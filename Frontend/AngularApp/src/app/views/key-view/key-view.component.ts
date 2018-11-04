import { Component, OnInit } from '@angular/core';
import { LanguageKey} from '../../models/LanguageKey';
import {VersionService} from '../../services/versions/versions.service';
import {LanguagesService} from '../../services/languages/languages.service';
import {KeysService} from '../../services/keys/keys.service';
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
  currLang: Language;
  currVersion = '';
  currLanguage = '';


  criteria = '';

  constructor(
    private versionService: VersionService,
    private languageService: LanguagesService,
    private keySevice: KeysService
  ) { }

  async ngOnInit() {
    await this.getVersions();
    await this.getLanguages();
    await this.getKeyList();

  }

  async getVersions() {
    const versions = await this.versionService.getAll().toPromise();
    this.model.versions = versions;
      this.currVersion = this.model.versions[0].verNum;
      console.log(this.currVersion);
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

  async getKeyList(){

    console.log(this.currLanguage + '' + this.currVersion);
    let tempString = this.currVersion;
    if(this.currVersion.indexOf('.') > -1){
      tempString = tempString.replace(/\./g, '_');
    }
    console.log(tempString);

    const resultList =  await this.keySevice.getNewKeys(this.currLanguage, tempString).toPromise();

    this.keys = resultList;
    console.log(this.keys);
  }

}

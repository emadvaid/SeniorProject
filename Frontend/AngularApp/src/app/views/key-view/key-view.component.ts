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
  englisTranslation = 'none';
  keys = []; //keylist must be static
  keys2 = [];  //this keylist can change
  englishKeys = [];
  sortedKeys = [];
  versionLanguageList = [];


  model: any = {};
  languages: any = {};
  //current selected key
  currKey: LanguageKey = {keyId: -1,
       languageCode: 'none',
       LanguageVersion: 'none',
       keyName: 'none',
       keyApproved: false,
       keyNew: false,
       keyVariant: 'none',
       keyNote: 'none',
       sectionId: 'none',
       sectionNote: 'none',
       fileName: 'none',
       fileNotes: 'none',
  };

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
    this.getEnglishKeys();
    console.log(this.currKey.keyVariant);

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
  //returns all the english keys for this version, needed for key comparison
  async getEnglishKeys(){
    let tempString = this.currVersion;
    if(this.currVersion.indexOf('.') > -1){
      tempString = tempString.replace(/\./g, '_');
    }
    console.log(tempString);

    const resultList =  await this.keySevice.getNewKeys('en', tempString).toPromise();

    this.englishKeys = resultList.keysDetails;
  }


  //this is all the keys for the current language
  async getKeyList(){

    console.log(this.currLanguage + '' + this.currVersion);
    let tempString = this.currVersion;
    if(this.currVersion.indexOf('.') > -1) {
      tempString = tempString.replace(/\./g, '_');
    }
    console.log(tempString);

    const resultList =  await this.keySevice.getNewKeys(this.currLanguage, tempString).toPromise();

    this.keys = resultList.keysDetails;
    this.keys = this.keys.sort(function(a, b) {
      let textA = a.keyName.toUpperCase();
      let textB = b.keyName.toUpperCase();
      return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
    });
    this.keys2 = this.keys;
    console.log(this.keys);
  }

  selectKey(key: LanguageKey){
    this.currKey = key;
    for(let thiskey of this.englishKeys){
      if(thiskey.keyName === this.currKey.keyName){
        this.englisTranslation = thiskey.keyVariant;
        break;
      }
    }

    console.log(this.currKey);
  }

  searchList(criteria: string){
    this.keys2 = [];
    this.keys2.length = 0;
    if(criteria == null || criteria == ''){
      this.keys2 = this.keys;
    }else {
      for(let key1 of this.keys){
        if(key1.keyName.includes(criteria)){
         this.keys2.push(key1);}
      }
    }
  }

}

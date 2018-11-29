import { Component, OnInit } from '@angular/core';
import { LanguageKey } from '../../models/LanguageKey';
import { VersionService } from '../../services/versions/versions.service';
import { LanguagesService } from '../../services/languages/languages.service';
import { KeysService } from '../../services/keys/keys.service';
import { Version } from 'src/app/models/Version';
import { Language } from 'src/app/models/Language';
import { StatisticsService } from '../../services/statistics/statistics.service';
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { UserService } from 'src/app/services/users/user.service';
import {UserLoginService} from '../../services/user.login/user.login.service';
import { User, UserTypes } from '../../models/User';


@Component({
  selector: 'app-key-view',
  templateUrl: './key-view.component.html',
  styleUrls: ['./key-view.component.css']
})
export class KeyViewComponent implements OnInit {
  englishTranslation = 'none';
  approvalSelection = 'All';
  keys = []; // keylist must be static
  keys2 = [];  // this keylist can change
  keys3 = [];
  keys4 = [];

  userLanguages: User;

  available = true;

  resetKeysList = [];
  englishKeys = [];
  sortedKeys = [];
  versionLanguageList = [];


  model: any = {};
  languages: any = {};
  // current selected key
  currKey: LanguageKey = {
    keyId: -1,
    languageCode: 'none',
    languageVersion: 'none',
    keyName: 'none',
    keyApproved: false,
    keyNew: false,
    keyVariant: 'none',
    keyNote: 'none',
    sectionId: 'none',
    sectionNote: 'none',
    fileName: 'none',
    fileNotes: 'none',
    username: 'none'
  };

  resetKey: LanguageKey;

  // current Data
  currLang: Language;
  currVersion = 'None';
  currLanguage = 'None';

  searchType = 'Key';

  currLanguageFull = '';
  newKeys: String;
  totalKeys: String;
  approvedKeys: String;
  dupString: String;
  duplicateKeys = 0;


  criteria = '';

  constructor(
    private versionService: VersionService,
    private languageService: LanguagesService,
    private keySevice: KeysService,
    private statisticsService: StatisticsService,
    private cookies: CookieService,
    private userService: UserService,
    private userLoginService: UserLoginService,
  ) { }

  async ngOnInit() {
    this.approvalSelection = 'All';
    await this.getVersions();

    await this.getLanguages();
    // await this.viewStatistics();

    //this.getLanguages();
    //await this.viewStatistics();


  }

  async getVersions() {
    const versions = await this.versionService.getAll().toPromise();
    this.model.versions = versions;
    // this.currVersion = this.model.versions[0].verNum;
    console.log(this.currVersion);
  }

  async getLanguages() {
    //const language = await this.userService.getByUsername(username).toPromise();

    switch (this.userLoginService.getUserType) {
      case UserTypes.admin:
        const languages = await this.languageService.getAll().toPromise();
        this.languages.lang = languages;
        break;
      case UserTypes.dealer:
        this.userLanguages = await this.userService.getByUsername(this.cookies.get('username')).toPromise();
        this.languages.lang = this.userLanguages.languages;
        break;
      default:
        console.log('nothing');
    }


    //const languages = await this.languageService.getAll().toPromise();


    console.log(this.currLanguage);

  }
  // returns all the english keys for this version, needed for key comparison
  async getEnglishKeys() {
    let tempString = this.currVersion;
    if (this.currVersion.indexOf('.') > -1) {
      tempString = tempString.replace(/\./g, '_');
    }

    const resultList = await this.keySevice.getNewKeys('en', tempString).toPromise();
    console.log('english keys');
    console.log(resultList);

    this.englishKeys = resultList.keysDetails;
  }


  //  is all the keys for the current language
  async getKeyList() {
    this.viewStatistics();


    console.log(this.currLanguage + '' + this.currVersion);
    let tempString = this.currVersion;
    if (this.currVersion.indexOf('.') > -1) {
      tempString = tempString.replace(/\./g, '_');
    }
    console.log(tempString);
    await this.getEnglishKeys();

    const resultList = await this.keySevice.getNewKeys(this.currLanguage, tempString).toPromise();

    this.keys = resultList.keysDetails;
    this.resetKeysList = resultList.keysDetails;

    // temporarily set keys to english in order to sort list
    this.keys3 = this.keys;
    for(let key of this.keys3){
      for(let key2 of this.englishKeys){
        if(key.keyName == key2.keyName){
          key.keyVariant = '' + key2.keyVariant + '';
        }
      }
    }

    this.keys3 = this.keys.sort(function (a, b) {
      let textA = a.keyVariant.toUpperCase();
      let textB = b.keyVariant.toUpperCase();
      return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
    });
    //end sort
    var result = await this.keySevice.getNewKeys(this.currLanguage, tempString).toPromise();
    console.log('dddddd');
    console.log(result);

    //set key variant back to original
    for(let key of this.keys3){
      for(let key2 of result.keysDetails){
        if(key.keyName === key2.keyName){
          console.log(key2.keyVariant);
          key.keyVariant = '' + key2.keyVariant + '';
        }
      }
    }
    //set Language display
    for (let lang of this.languages.lang){
      if (this.currLanguage == lang.langCode){
        this.currLanguageFull = ' in ' + lang.langName;
      }
    }
    var bool = true;
    this.duplicateKeys = 0;
    this.keys2 = [];
    this.keys4 = [];


     //remove duplicate keys of same key name
    for(let key of this.keys3){
      for(let key2 of this.keys4){
        if(key.keyName === key2.keyName){
          bool = false;
          this.duplicateKeys = +this.duplicateKeys + 1;
          break;
        }
        else{
          bool = true;
        }
      }
      if(bool == true) {
        this.keys2.push(key);
      }
      this.keys4.push(key);
    }
    this.dupString = '' + this.duplicateKeys;
    //this.keys2 = this.keys3;
    console.log('keys:');
    console.log(this.keys);
  }

  selectKey(key: LanguageKey) {
    this.getResetKey(key);
    this.currKey = key;
    for (let thiskey of this.englishKeys) {
      if (thiskey.keyName === this.currKey.keyName) {
        this.englishTranslation = thiskey.keyVariant;
        break;
      }
    }

    console.log(this.currKey);
  }

  searchList(criteria: string) {
    this.keys2 = [];
    this.keys2.length = 0;
    if (this.approvalSelection == 'All') {
      if (criteria == null || criteria == '') {
        this.keys2 = this.keys;
      } else {
        for (let key1 of this.keys) {
          this.pushKeyAllApprove(key1, criteria, this.searchType);}
      }
    }
    else if (this.approvalSelection == 'Approved') {
      if (criteria == null || criteria == '') {
        for (let key1 of this.keys) {
          if (key1.approved == true) {
            this.keys2.push(key1);}
        }
      } else {
        for (let key1 of this.keys) {
          this.pushKeyCriteria(key1, criteria, this.searchType, true);}
      }
    }
    else {

      if (criteria == null || criteria == '') {
        for (let key1 of this.keys) {
          if (key1.approved == false) {
            this.keys2.push(key1);}
        }
      } else {
        for (let key1 of this.keys) {
          this.pushKeyCriteria(key1, criteria, this.searchType, false);}
      }
    }

  }

  pushKeyCriteria(key: any, criteria: string, strType: string, bool: Boolean){
    if(strType == 'Key'){

      let str = this.returnEnglishName(key)
      if (str.includes(criteria) && key.approved == bool) {
        this.keys2.push(key);}
    }
    else if(strType == 'Translated Variant'){
      if (key.keyVariant.includes(criteria) && key.approved == bool) {
        this.keys2.push(key);}
    }
    else{
      if (key.keyNote.includes(criteria) && key.approved == bool) {
        this.keys2.push(key);}
    }
  }

  pushKeyAllApprove(key: any, criteria: string, strType: string){
    if(strType == 'Key'){
      let str = this.returnEnglishName(key)
      if (str.includes(criteria)) {
        this.keys2.push(key);}
    }
    else if(strType == 'Translated Variant'){
      if (key.keyVariant.includes(criteria)) {
        this.keys2.push(key);}
    }
    else{
      if (key.keyNote.includes(criteria)) {
        this.keys2.push(key);}
    }
  }

  updateKeys() {
    let username = this.cookies.get('username');
    this.currKey.username = username;
    this.currKey.languageCode = this.currLanguage;
    this.currKey.languageVersion = this.currVersion;
    this.currKey.keyApproved = true;
    this.keySevice.updateKey(this.currKey).toPromise();
    this.viewStatistics();
    this.getKeyList();
    var alert = document.getElementById("success-alert");

    //alert.hidden = false;
    setTimeout(function () {
      alert.hidden = false;
    }, 1000);
    setTimeout(function () {
      alert.hidden = true;
    }, 3000);
  }

  //saves key without accepting it
  saveKeys() {
    this.currKey.languageCode = this.currLanguage;
    this.currKey.languageVersion = this.currVersion;
    this.keySevice.updateKey(this.currKey).toPromise();
    this.viewStatistics();
    this.getKeyList();
    var alert = document.getElementById("success-alert");
    //alert.hidden = false;
    setTimeout(function () {
      alert.hidden = false;
    }, 1000);
    setTimeout(function () {
      alert.hidden = true;
    }, 3000);
  }

  async viewStatistics() {
    //Statistics for first version
    this.statisticsService.getNewKeys(this.currLanguage, this.currVersion).subscribe(
      (keys) => {
        this.newKeys = JSON.stringify(keys);
        console.log(this.newKeys);
      }
    )

    this.statisticsService.getApprovedKeys(this.currLanguage, this.currVersion).subscribe(
      (keys) => {
        this.approvedKeys = JSON.stringify(keys);
        console.log(this.approvedKeys);
      }
    )
    this.statisticsService.getTotalKeys(this.currLanguage, this.currVersion).subscribe(
      (keys) => {
        this.totalKeys = JSON.stringify(keys);
        console.log(this.totalKeys);
      }
    );
  }
openModal(){
  let modal = document.getElementById('changeVersion');
  modal.style.display = 'block';
}

  closeModal(){
    let modal = document.getElementById('changeVersion');
    modal.style.display = 'none';
  }
  submitTable(){
    if(this.currLanguage == 'None'){
      this.currLanguage = this.languages.lang[0].langCode;
    }
    if(this.currVersion == 'None'){
      this.currVersion = this.model.versions[0].verNum;
    }
    this.getKeyList();
    this.approvalSelection = 'All'
    let modal = document.getElementById('changeVersion');
    modal.style.display = 'none';

    this.currKey = {
      keyId: -1,
      languageCode: 'none',
      languageVersion: 'none',
      keyName: 'none',
      keyApproved: false,
      keyNew: false,
      keyVariant: 'none',
      keyNote: 'none',
      sectionId: 'none',
      sectionNote: 'none',
      fileName: 'none',
      fileNotes: 'none',
      username: 'none'
    };
    this.englishTranslation = 'none';
    this.available = true;

  }
   async getResetKey(key: LanguageKey){
  }

  async keyReset(key: LanguageKey){
    let tempString = this.currVersion;
    if (this.currVersion.indexOf('.') > -1) {
      tempString = tempString.replace(/\./g, '_');
    }
    const resultList = await this.keySevice.getNewKeys(this.currLanguage, tempString).toPromise();
    this.resetKeysList = resultList.keysDetails;
    for(let key2 of this.resetKeysList){
      if(key.keyName == key2.keyName){
        this.resetKey = key2;
      }
    }
    this.currKey = this.resetKey;
    console.log(this.resetKey);
  }

  returnEnglishName(key: LanguageKey): string{
    for(let key1 of this.englishKeys){
      if(key.keyName == key1.keyName){
        return key1.keyVariant;
      }
    }
  }
}

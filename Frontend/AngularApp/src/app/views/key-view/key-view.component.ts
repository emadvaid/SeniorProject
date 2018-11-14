import { Component, OnInit } from '@angular/core';
import { LanguageKey } from '../../models/LanguageKey';
import { VersionService } from '../../services/versions/versions.service';
import { LanguagesService } from '../../services/languages/languages.service';
import { KeysService } from '../../services/keys/keys.service';
import { Version } from 'src/app/models/Version';
import { Language } from 'src/app/models/Language';
import { StatisticsService } from '../../services/statistics/statistics.service';


@Component({
  selector: 'app-key-view',
  templateUrl: './key-view.component.html',
  styleUrls: ['./key-view.component.css']
})
export class KeyViewComponent implements OnInit {
  englishTranslation = 'none';
  approvalSelection = 'All';
  keys = []; //keylist must be static
  keys2 = [];  //this keylist can change
  englishKeys = [];
  sortedKeys = [];
  versionLanguageList = [];


  model: any = {};
  languages: any = {};
  //current selected key
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
  };

  //current Data
  currLang: Language;
  currVersion = 'None';
  currLanguage = 'None';
  newKeys: String;
  totalKeys: String;
  approvedKeys: String;


  criteria = '';

  constructor(
    private versionService: VersionService,
    private languageService: LanguagesService,
    private keySevice: KeysService,
    private statisticsService: StatisticsService,
  ) { }

  async ngOnInit() {
    this.approvalSelection = 'All';
    await this.getVersions();
    await this.getLanguages();
    //await this.viewStatistics();

  }

  async getVersions() {
    const versions = await this.versionService.getAll().toPromise();
    this.model.versions = versions;
    //this.currVersion = this.model.versions[0].verNum;
    console.log(this.currVersion);
  }

  async getLanguages() {
    const languages = await this.languageService.getAll().toPromise();
    this.languages.lang = languages;
    console.log(languages);
    //this.currLanguage = this.languages.lang[0].langCode;
    console.log(this.currLanguage);

  }
  //returns all the english keys for this version, needed for key comparison
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


  //this is all the keys for the current language
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
    this.keys = this.keys.sort(function (a, b) {
      let textA = a.keyName.toUpperCase();
      let textB = b.keyName.toUpperCase();
      return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
    });
    this.keys2 = this.keys;
    console.log('keys:');
    console.log(this.keys);
  }

  selectKey(key: LanguageKey) {
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
          if (key1.keyName.includes(criteria)) {
            this.keys2.push(key1);
          }
        }
      }
    }
    else if (this.approvalSelection == 'Approved') {
      if (criteria == null || criteria == '') {
        for (let key1 of this.keys) {
          if (key1.approved == true) {
            this.keys2.push(key1);
          }
        }
      } else {
        for (let key1 of this.keys) {
          if (key1.keyName.includes(criteria) && key1.approved == true) {
            this.keys2.push(key1);
          }
        }
      }
    }
    else {

      if (criteria == null || criteria == '') {
        for (let key1 of this.keys) {
          if (key1.approved == false) {
            this.keys2.push(key1);
          }
        }
      } else {
        for (let key1 of this.keys) {
          if (key1.keyName.includes(criteria) && key1.approved == false) {
            this.keys2.push(key1);
          }
        }
      }

    }
  }

  updateKeys() {
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
  }
  consoleii(){
    console.log(this.currLanguage);
  }
}

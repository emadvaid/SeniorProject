import { Component, OnInit } from '@angular/core';
import { FileFunctionsService } from '../../../services/file_functions/file-functions.service';
import {VersionService} from '../../../services/versions/versions.service';
import {LanguagesService} from '../../../services/languages/languages.service';


@Component({
  selector: 'app-export-files',
  templateUrl: './export-files.component.html',
  styleUrls: ['./export-files.component.css']
})
export class ExportFilesComponent implements OnInit {
  public versionNumber = '';
  public language = '';
  model: any = {};
  currVersion = '';
  currLanguage = '';
  languages: any = {};

  constructor(
    private fileFunctionsService: FileFunctionsService,
    private versionService: VersionService,
    private languageService: LanguagesService,
  ) { }

  async ngOnInit() {
    await this.getVersions();
    await this.getLanguages();
    this.language = this.currLanguage;
    this.versionNumber = this.currVersion;
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

  changeVersion() {
    this.versionNumber = this.currVersion;
    console.log(this.versionNumber);
  }
  changeLanguage() {
    this.language = this.currLanguage;
    console.log(this.language);
  }

  onSubmit() {
    console.log(this.language);
    console.log(this.versionNumber);
    this.fileFunctionsService.exportFiles(this.language, this.versionNumber).subscribe();
  }
}

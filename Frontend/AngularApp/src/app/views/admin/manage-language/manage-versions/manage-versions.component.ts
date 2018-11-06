import { Component, OnInit, Version } from '@angular/core';
import { VersionService } from 'src/app/services/versions/versions.service';
import { Http } from '@angular/Http';
import { Router } from '@angular/router';
import { LanguagesService } from 'src/app/services/languages/languages.service';
import { Language } from 'src/app/models/Language';


@Component({
selector: 'app-manage-versions',
templateUrl: './manage-versions.component.html',
styleUrls: ['./manage-versions.component.css']
})

export class ManageVersionComponent implements OnInit {
    model: any = { };

    constructor(private versionService: VersionService, private langaugeService:
      LanguagesService, private http: Http, private router: Router) {}
    public ngOnInit() {}

  deleteLanguageByVersion(event: any) {
    const langCode = event.target.dataset['langCode'];
    const verNum = event.target.dataset['vernum'];

    this.versionService.deleteLanguageByVer(langCode, verNum)
    .subscribe(
      res => {
        console.log('file-DeclareFunctionStmt.deleteLanguageByVersion: results: ', res);
      },
      err => {
        console.log('file-DeclareFunctionStmt.deleteLanguageByVersion: error: ', err);
      }
    );
  }

  getByVersion(event: any) {
    const versionNumber = event.targer.dataset['versionNumber'];
    this.langaugeService.getByVersion()
    .subscribe(
      res => {
        console.log('file-DeclareFunctionStmt.getByVersion: results: ', res);
      },
      err => {
        console.log('file-DeclareFunctionStmt.getByVersion: error: ', err);
      }
    );
  }
  private refresh(): void {
    this.langaugeService.getByVersion().subscribe(
        (languages: Array<Language>) => {
          console.log('language', languages );
            this.model.langCode = languages;
        },
        (err: any) => {
            console.log('ManageVersion: error getting versions ', err);
        }
    );
}

}

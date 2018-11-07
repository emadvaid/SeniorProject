import { Component, OnInit } from '@angular/core';
import { VersionService } from 'src/app/services/versions/versions.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { LanguagesService } from 'src/app/services/languages/languages.service';
import { Version } from 'src/app/models/Version';


@Component({
selector: 'app-manage-versions',
templateUrl: './manage-versions.component.html',
styleUrls: ['./manage-versions.component.css']
})

export class ManageVersionComponent implements OnInit {
    model: any = { };
    langCode: string = null;

  constructor(
    private versionService: VersionService,
    private langaugeService: LanguagesService,
    private activatedRoute: ActivatedRoute,
    private router: Router) {
  }

  public ngOnInit() {

    // look for a query Param named langCode, so we can load the language versions,
    //   set the object instance variable langCode, then call refresh
    this.activatedRoute.queryParams.subscribe(
      (parms: Params) => {
        this.langCode = parms['langCode'];
        this.refresh();
      }
    );
  }

  refresh() {
    // call versionService.getByLangCode to populate the version table,
    //   for this given language
    this.versionService.getByLangCode(this.langCode)
      .subscribe(
        (versions: Version[]) => {
          this.model.versions = versions;
          console.log('manageVersionComponent.refresh(): service returned: ', versions);
        },
        (err: any) => {
          console.log('manageVersionComponent.refresh(): service threw err: ', err);
        }
      );
  }

  deleteLanguageByVersion(event: any) {
    const langCode = event.target.dataset['langCode'];
    const verNum = event.target.dataset['vernum'];

    this.versionService.deleteLanguageByVer(this.langCode, verNum)
    .subscribe(
      res => {
        this.refresh();
        console.log('file-DeclareFunctionStmt.deleteLanguageByVersion: results: ', res);
      },
      err => {
        this.refresh();
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
}

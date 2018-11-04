import { Component, OnInit } from '@angular/core';
import { VersionService } from 'src/app/services/versions/versions.service';
import { Http } from '@angular/Http';
import { Router } from '@angular/router';


@Component({
selector: 'app-delete-language-by-ver-num',
templateUrl: './delete-languageByVerNum.component.html',
styleUrls: ['./delete-languageByVerNum.component.css']
})

export class DeleteLanguageByVerNumComponent implements OnInit {
    model: any = { };

    constructor(private versionService: VersionService, private http: Http, private router: Router) {}
    public ngOnInit() {}

  deleteLanguageByVersion(event: any) {
    const verNum = event.target.dataset['vernum'];
    const langCode = event.target.dataset['langCode'];

    this.versionService.deleteLanguageByVer(langCode, verNum)
    .subscribe(
      res => {
        console.log('file-DeclareFunctionStmt.deleteLanguageByVersio: results: ', res);
      },
      err => {
        console.log('file-DeclareFunctionStmt.deleteLanguageByVersio: error: ', err);
      }
    );
  }

}

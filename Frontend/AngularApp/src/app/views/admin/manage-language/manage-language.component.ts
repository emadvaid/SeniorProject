import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Language } from '../../../models/Language';
import { LanguagesService } from 'src/app/services/languages/languages.service';

@Component({
    selector: 'app-manage-language',
    templateUrl: './manage-language.component.html',
    styleUrls: ['./manage-language.component.css']
  })
export class ManageLanguageComponent implements OnInit {
    model: any = { };

    constructor(private languageService: LanguagesService, private router: Router) {}

    public ngOnInit() {
        this.refresh();
    }

    private refresh(): void {
        this.languageService.getAll().subscribe(
            (languages: Array<Language>) => {
                this.model.languages = languages;
            },
            (err: any) => {
                console.log('ManageLanguageComponent: error getting languages', err);
            }
        );
    }

    createLanguages() {
        this.router.navigate(['admin/createLanguage']);
    }

    manageVersions(event: any) {
        // console.log(`event.target`, event.target);
        // console.log(`event.target.dataset`, event.target.dataset);
        // alert(`event.target.dataset['lang-code']=${event.target.dataset['lang-code']}`);
        this.router.navigate(['admin/manageVersions'], {
            queryParams: {langCode: event.target.dataset['langCode']}
        });

    }
    get diagnostics() {
        return 'model = ' + JSON.stringify(this.model);
    }

}

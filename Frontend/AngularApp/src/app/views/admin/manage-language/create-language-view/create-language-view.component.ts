import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Http } from '@angular/Http';
import { error } from 'util';
import { Router } from '@angular/router';
import { LanguagesService } from 'src/app/services/languages/languages.service';
import { Language } from 'src/app/models/Language';
import { CookieService } from 'angular2-cookie/services/cookies.service';




@Component({
    selector: 'app-create-language-view',
    templateUrl: './create-language-view.component.html',
    styleUrls: ['./create-language-view.component.css']
  })

export class CreateLanguageViewComponent implements OnInit {
  model: any = {};
  submitted: boolean;
  @Output() save: EventEmitter<any> = new EventEmitter();
  @Output() cancel: EventEmitter<any> = new EventEmitter();

  constructor(
    private languageService: LanguagesService,
    private cookies: CookieService) {}

  public ngOnInit() {
        this.model = {};
        this.submitted = false;
  }

  onSubmit(): void {
    // validate
    if (!this.validate()) {
        console.log('onSubmit: validation error');
        return;
    }
    // call service method(
    this.submitted = true;

    const newLanguage: Language = new Language();
    newLanguage.langName = this.model.langName;
    newLanguage.langCode = this.model.langCode;
    let username = this.cookies.get('username');
    newLanguage.username = username;
    
    console.log(newLanguage);
    console.log(JSON.stringify(newLanguage));

    this.languageService.create(newLanguage)
    .subscribe((language: Language) => { // succesfully created a new user so redirect to the manage user page
        this.save.emit('saved');
    });


}

onCancel() {
    //
    this.cancel.emit('canceled');
}


validate(): boolean {
    console.log('this.model.langName', this.model.langName,
        'this.isEmpty (this.model.langName)', this.isEmpty (this.model.langName));
    console.log('this.model.langCode', this.model.langCode,
        'this.isEmpty (this.model.langCode)', this.isEmpty (this.model.langCode));

    return (
        !this.isEmpty (this.model.langName)
        && !this.isEmpty(this.model.langCode)
    );
}
isEmpty(str: string): boolean {
    if (str && typeof(str) === 'string') {
        if (str.length > 0) {
            return false;
        }
    }

    return true;
}

get diagnostics() {
    return 'model = ' + JSON.stringify(this.model)
      + ', submitted = ' + this.submitted;
  }
}

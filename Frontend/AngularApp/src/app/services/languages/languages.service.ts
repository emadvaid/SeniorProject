import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class LanguagesService {
    private langArry = [
        {name: 'Brazillian Portugues'},
        {name: 'English'},
        {name: 'Canadian French'},
        {name: 'German'},
        {name: 'Italian'},
        {name: 'Japanese'},
        {name: 'Korean'},
        {name: 'Russian'},
        {name: 'Spanish'},
        {name: 'Swedish'},
        {name: 'Simplified Chinese'},
        {name: 'Traditional Chinese'},
        {name: 'Thai'},
        {name: 'Vietnamese'},
    ];

    public get languages(): any {
        return this.langArry.slice();
    }

    public get defaultLanguage(): any {
        return 'English';
    }
}

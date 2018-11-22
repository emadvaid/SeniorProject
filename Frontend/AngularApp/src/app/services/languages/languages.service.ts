import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import {map, catchError} from 'rxjs/operators';
import { Http, Headers, RequestOptions } from '@angular/Http';
import { UserLoginService } from '../user.login/user.login.service';

import { Language} from '../../models/Language';
import { VersionService } from '../versions/versions.service';

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
    constructor( private http: Http, private userLoginService: UserLoginService) {}

    getAll() {


        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json'

        });
        const options = new RequestOptions({ headers: headers});

        return this.http.get('/api/lang', options)
        .pipe(
            map((resp: any) => {
                console.log('ManageLanguageComponent ajax response: ', resp);
                if (resp) {
                    const respBody = resp.json();
                    console.log('ManageLanguageComponent loaded languages list: ', respBody);
                    if (respBody.langDetails) {
                        return <Array<Language>> respBody.langDetails;
                    }
                }

                return [];
            })
        )
        .pipe(catchError(err => this.handleError(err)));
    }

    getByVersion() {


        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json'

        });
        const options = new RequestOptions({ headers: headers});

        return this.http.get('/api/lang/version/{versionNumber}', options)
        .pipe(
            map((resp: any) => {
                console.log('ManageLanguageComponent ajax response: ', resp);
                if (resp) {
                    const respBody = resp.json();
                    console.log('ManageLanguageComponent loaded languages list: ', respBody);
                    if (respBody.versionNumber) {
                        return <Array<Language>> respBody.versionNumber;
                    }
                }

                return [];
            })
        )
        .pipe(catchError(err => this.handleError(err)));
    }


    create(langDetails: Language): Observable<Language> {
        if (!this.userLoginService.isLoggedIn) {
            console.log('UserService.createUser: user not logged in');
            return null;
        }
             // Add in JSON header and access token header
             const headers = new Headers({
                'Content-Type': 'application/Json',
                'access-token': this.userLoginService.accessToken
            });
            const options = new RequestOptions({ headers: headers});

            const msg = { langDetails };

            console.log(JSON.stringify(msg));


            return this.http.post('/api/lang', msg, options)
            .pipe(
                map((resp: any) => {
                    if (resp && resp.langDetails) {
                        {
                            const respBody = resp.json();
                            console.log('respBody = ', respBody);

                            const newLanguage = respBody.langDetails;

                            console.log('language = ', newLanguage);

                            return newLanguage;
                    }
                    return null;
                }
            }))
            .pipe(catchError(err => this.handleError(err)));
    }

    public get defaultLanguage(): any {
        return 'English';
    }

    private handleError(err: any): Observable<Language> {
        //
        console.log('create user failed: err =', err);
        return of(null);
    }
}

import { Injectable } from '@angular/core';
import {map, catchError} from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Http, Headers, RequestOptions } from '@angular/Http';
import { UserLoginService } from '../user.login/user.login.service';
import {Version} from '../../models/Version';


@Injectable({providedIn: 'root'})
export class VersionService {

    constructor(private http: Http, private userLoginService: UserLoginService) {}

    getAll() {
        const headers = new Headers({
            'Content-Type': 'application/Json'
        });
        const options = new RequestOptions({ headers: headers});

        return this.http.get('http://localhost:8080/versions', options)
        .pipe(
            map((resp: any) => {
                console.log(' file-functions.component ajax response: ', resp);
                if (resp) {
                    const respBody = resp.json();
                    console.log(' file-functions.component language list: ', respBody);
                   if (respBody.VersionDetails) {
                        return <Array<Version>> respBody.VersionDetails;
                    }

                }
                return [];
            })
        )
        .pipe(catchError(err => this.handleError(err)));
    }

    create(VersionDetails: Version): Observable<Version> {
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

        return this.http.post('http://localhost:8080/versions', {
            versionDetails: {
                verNum: VersionDetails.verNum
            }
        }, options)
        .pipe(
            map((resp: any) => {
                if (resp) {
                    const respBody = resp.json();
                    console.log('VersionService.create: respBody', respBody);
                    const newVersion = respBody.versionDetails;
                    return newVersion;
                }
                return null;
            })
        )
        .pipe(catchError(err => this.handleError(err)));
    }

    private handleError(err: any): Observable<Version> {
        console.log('create version  failed: err =', err);
        return of(null);
    }
}

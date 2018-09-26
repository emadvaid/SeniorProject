
import { Injectable } from '@angular/core';
import { User, UserTypes } from '../../models/User';
import { Observable, of } from 'rxjs';
import {map, catchError} from 'rxjs/operators';
import { Http, Headers, RequestOptions } from '@angular/Http';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: Http) {}

    createUser(user: User): Observable<User> {

        const headers = new Headers({'Content-Type': 'application/Json'});
        const options = new RequestOptions({ headers: headers});

        return this.http.post('http://localhost:8080/users', {
            userDetails: user
        }, options)
        .pipe(
            map((resp: any) => {

                if (resp && resp.userDetails) {
                    const respBody = resp.json();
                    console.log('respBody = ', respBody);

                    const newUser = respBody.userDetails;

                    console.log('User = ', newUser);

                    return newUser;
                }

                return null;
            }))
            .pipe(catchError(err => this.handleError(err)));
    }

    private handleError(err: any): Observable<User> {
        //
        console.log('create user failed: err =', err);
        return of(null);
    }
}

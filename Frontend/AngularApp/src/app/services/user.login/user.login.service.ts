import {Injectable} from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Http, Response, Headers, RequestOptions } from '@angular/Http';
import {map} from 'rxjs/operators';

import { User, UserTypes } from '../../models/User';

@Injectable()
export class UserLoginService {
    private _accessToken: string = null;
    private _loggedInUser: User;

    constructor(private http: Http) {}

    public authenticateWithUsernamePassword(username, password): Observable<User> {

        // first log out the current user
        this.logout();

        const headers = new Headers({ 'Content-Type': 'application/json' });
        const  options = new RequestOptions({ headers: headers });

        return this.http.post('http://localhost:8080/authorizeUser', {
                username: username,
                password: password
        }, options).pipe(map((resp: any) => {
            if (resp) {
                const respBody = resp.json();

                console.log('authenticateWithUsernamePassword: ', respBody);

                // if the user is not active then deny the login
                if (!respBody.user.active) {
                    console.log('Tried to authenticate a inactive user');
                    this.logout();
                    return null;
                }

                this._accessToken = respBody.auth.accessToken;

                this._loggedInUser = new User();
                this._loggedInUser.id = respBody.user.id;
                this._loggedInUser.isActive =  respBody.user.active;
                this._loggedInUser.email =  respBody.user.email;
                this._loggedInUser.language1 =  respBody.user.language1;
                this._loggedInUser.language2 =  respBody.user.language2;
                this._loggedInUser.firstName =  respBody.user.firstName;
                this._loggedInUser.lastName =  respBody.user.lastName;
                this._loggedInUser.typeAsStr =  respBody.user.typeAsStr;

                // save the User and access token to local storage
                localStorage.setItem('access-token', this._accessToken);
                localStorage.setItem('user', JSON.stringify(this._loggedInUser));

                console.log('User = ', this._loggedInUser);

                return Object.assign({}, this._loggedInUser);
            }

            return null;
        })).pipe(catchError(err => this.handleError(err)));
    }

    public logout(): void {
        // clear the user variables
        this._loggedInUser = null;
        this._accessToken = null;

        // clear the local storage
        localStorage.clear();
    }

    public get getUserType(): UserTypes {
        if (!this.isLoggedIn || !this._loggedInUser) {
            return UserTypes.unknown;
        } else {
            return this._loggedInUser.type;
        }
    }

    public get user(): User {
        if (this.isLoggedIn) {
            return Object.assign({}, this._loggedInUser);
        } else {
            return null;
        }
    }

    public get accessToken(): string {
        if (this.isLoggedIn) {
            return this._accessToken.slice();
        } else {
            return null;
        }
    }

    public get isLoggedIn(): boolean {
        // fetch the user and access token from local storage, if not cached
        if (!this._accessToken || this._accessToken.length < 1) {
            this._accessToken = localStorage.getItem('access-token');

            const stringifiedUser = localStorage.getItem('user');

            if (stringifiedUser) {
                this._loggedInUser = JSON.parse(localStorage.getItem('user'));
            }
        }
        return this._accessToken && this._accessToken.length > 0;
    }

    public isUserActive(userType: UserTypes) {
        if (this.isLoggedIn) {
            return this._loggedInUser && this._loggedInUser.isActive;
        }

        return false;
    }

    public isUserType(userType: UserTypes) {
        if (this.isLoggedIn) {
            return this._loggedInUser && this._loggedInUser.type === userType;
        }

        return false;
    }

    private handleError(err: any): Observable<User> {
        //
        console.log('authentication failed: err =', err);
        return of(null);
    }
}

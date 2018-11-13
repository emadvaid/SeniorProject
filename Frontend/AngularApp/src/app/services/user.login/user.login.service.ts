import {Injectable} from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Http, Response, Headers, RequestOptions } from '@angular/Http';
import {map} from 'rxjs/operators';

import { User, UserTypes } from '../../models/User';

@Injectable({providedIn: 'root'})
export class UserLoginService {
    private _accessToken: string = null;
    private _loggedInUser: User = null;

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
                this._loggedInUser.username = respBody.user.username;
                this._loggedInUser.isActive =  respBody.user.active;
                this._loggedInUser.email =  respBody.user.email;
                this._loggedInUser.languages =  respBody.user.languages;
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

    /*
     *  This is a getter function, that tests to see if there is a currently logged in User.
     *   In this method we first check the class variables, and if the user is not valid,
     *   we then try to load the user from localstorage.
     */
    public get isLoggedIn(): boolean {
        // first check if there is a valid user, cached in the class variables
        if (this.validateAccessToken(this._accessToken) && this.validateUser(this._loggedInUser)) {
            // console.log('isLoggedIn(): user was cached');
            // cached user found return true
            return true;
        } else {
            // cached user not found so try to build from local storage
            console.log('isLoggedIn(): user is not cached');
            this._accessToken = localStorage.getItem('access-token');
            const stringifiedUser = localStorage.getItem('user');
            if (stringifiedUser != null) {
                this._loggedInUser = User.fromJsonString(stringifiedUser);

                // console.log('this.validateUser(this._loggedInUser)', this.validateUser(this._loggedInUser), this._loggedInUser);
            } else {
                // console.log('user not found in localstorage');
            }

            if (this.validateAccessToken(this._accessToken) && this.validateUser(this._loggedInUser)) {
                // console.log('isLoggedIn(): user found in local storage');
                return true;
            }
            // console.log('isLoggedIn(): user not found in local storage', this._accessToken, stringifiedUser, this._loggedInUser);
        }

        // could not find valid user in cache or localstorage
        this._accessToken = null;
        this._loggedInUser = null;
        localStorage.removeItem('access-token');
        localStorage.removeItem('user');

        return false;
    }

    private validateAccessToken(token: string): boolean {
        return ((typeof token !== 'undefined') && (token !== null) && (token.length > 1));
    }

    private validateUser(user: User) {
        return ((typeof user !== 'undefined') && (user.isActive === true) );
    }

    public isUserActive(userType: UserTypes) {
        if (this.isLoggedIn) {
            return this._loggedInUser != null && this._loggedInUser.isActive;
        }

        return false;
    }

    public isUserType(userType: UserTypes) {
        if (this.isLoggedIn) {
            return this._loggedInUser != null && this._loggedInUser.type === userType;
        }
        return false;
    }

    private handleError(err: any): Observable<User> {
        //
        console.log('authentication failed: err =', err);
        return of(null);
    }
}

import {Injectable} from '@angular/core';
import { User, UserTypes } from '../../models/User';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Http, Response, Headers, RequestOptions } from '@angular/Http';
import {map} from 'rxjs/operators';
@Injectable()
export class UserLoginService {
    private accessToken: string = null;
    private loggedInUser: User;

    constructor(private http: Http) {}

    authenticateWithUsernamePassword(username, password): Observable<User> {

        const headers = new Headers({ 'Content-Type': 'application/json' });
        const  options = new RequestOptions({ headers: headers });

        return this.http.post('http://localhost:8080/AuthorizeUser', {
                username: username,
                password: password
        }, options).pipe(map(resp => {
            if (resp) {
                const respBody = resp.json();

                this.accessToken = respBody.auth.accessToken;

                this.loggedInUser = new User();
                this.loggedInUser.id = respBody.user.id;
                this.loggedInUser.isActive =  respBody.user.active;
                this.loggedInUser.email =  respBody.user.email;
                this.loggedInUser.language1 =  respBody.user.language1;
                this.loggedInUser.language2 =  respBody.user.language2;
                this.loggedInUser.firstName =  respBody.user.firstName;
                this.loggedInUser.lastName =  respBody.user.lastName;

                const type: string =  respBody.user.type;

                switch (type) {
                    case 'admin':
                        this.loggedInUser.type = UserTypes.admin;
                        break;
                    case 'dealer':
                        this.loggedInUser.type = UserTypes.dealer;
                        break;
                    default:
                        this.loggedInUser.type = UserTypes.unknown;
                }
                console.log('User = ', this.loggedInUser);

                return Object.assign({}, this.loggedInUser);
            }

            return null;
        })).pipe(catchError(err => this.handleError(err)));
    }

    logout(): void {
        this.loggedInUser = null;
        this.accessToken = null;
    }

    get getUserType(): UserTypes {
        if (!this.isLoggedIn || !this.loggedInUser) {
            return UserTypes.unknown;
        } else {
            return this.loggedInUser.type;
        }
    }

    get isLoggedIn(): boolean {
        return this.accessToken && this.accessToken.length > 0;
    }

    isUserType(userType: UserTypes) {
        if (this.isLoggedIn) {
            return this.loggedInUser && this.loggedInUser.type === userType;
        }

        return false;
    }

    private handleError(err: any): Observable<User> {
        //
        console.log('authentication failed: err =', err);
        return of(null);
    }
}

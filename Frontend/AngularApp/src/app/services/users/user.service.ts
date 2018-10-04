
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import {map, catchError} from 'rxjs/operators';
import { Http, Headers, RequestOptions } from '@angular/Http';

import { User, UserTypes } from '../../models/User';
import { UserLoginService } from '../user.login/user.login.service';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private roleArry = [
        {name: 'admin'},
        {name: 'dealer'}
    ];

    constructor(private userLoginService: UserLoginService, private http: Http) {}

    createUser(user: User): Observable<User> {

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

    public validateUserId(userId: string) {
        return ((typeof userId !== 'undefined') && (userId != null) && (userId.length > 0) );
    }

    getAllUsers() {

        if (!this.userLoginService.isLoggedIn) {
            console.log('UserService.getAllUsers: user not logged in');
            return null;
        }

        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json',
            'access-token': this.userLoginService.accessToken
        });
        const options = new RequestOptions({ headers: headers});

        return this.http.get('http://localhost:8080/users', options)
        .pipe(
            map((resp: any) => {
                console.log('ManageUsersComponent ajax response: ', resp);
                if (resp) {
                    const respBody = resp.json();
                    console.log('ManageUsersComponent loaded user list: ', respBody);
                    if (respBody.users) {
                        return <Array<User>> respBody.users;
                    }
                }

                return [];
            })
        )
        .pipe(catchError(err => this.handleError(err)));
    }

    public getUserById(userId: string): Observable<User> {
        const user: User = new User();
        user.id = 1;
        user.username = 'test user';
        user.firstName = 'first';
        user.lastName = 'first';
        user.email = 'test@test.com';
        user.language1 = 'English';
        user.isActive = true;
        user.typeAsStr = 'Dealer';

        return of(user);
    }

    public get userRoles(): any {
        return this.roleArry.slice();
    }

    private handleError(err: any): Observable<User> {
        //
        console.log('create user failed: err =', err);
        return of(null);
    }
}

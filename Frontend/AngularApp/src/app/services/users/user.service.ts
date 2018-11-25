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

    create(user: User): Observable<User> {

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

    getAll() {

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

    public getById(userId: string): Observable<User> {

        if (!this.userLoginService.isLoggedIn) {
            console.log('UserService.update: user not logged in');
            return null;
        }

        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json',
            'access-token': this.userLoginService.accessToken
        });
        const options = new RequestOptions({ headers: headers});
        return this.http.get(`api/user/${userId}`, options)
        .pipe(
            map((resp: any) => {
                // find the user from the response body
                if (resp && resp != null) {
                    const respBody: any = resp.json();
                    if (respBody && respBody != null) {
                        const { userDetail } = respBody;

                        if (userDetail && userDetail != null) {
                            return User.fromJsonObject(userDetail);
                        }
                    }
                }
                // no response so no user
                return null;
            }));
    }

    public getByUsername(username: string): Observable<User> {
        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json',
            'access-token': this.userLoginService.accessToken
        });
        const options = new RequestOptions({ headers: headers});
        const URL = 'api/username'
        return this.http.get(`${URL}/${username}`, options)
        .pipe(
            map((resp: any) => {
                // find the user from the response body
                if (resp && resp != null) {
                    const respBody: any = resp.json();
                    if (respBody && respBody != null) {
                        const { userDetail } = respBody;

                        if (userDetail && userDetail != null) {
                            return User.fromJsonObject(userDetail);
                        }
                    }
                }
                // no response so no user
                return null;
            }));
    }

    public update(userId: string, userDetail: User): Observable<boolean> {

        if (!this.userLoginService.isLoggedIn) {
            console.log('UserService.update: user not logged in');
            return null;
        }

        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json',
            'access-token': this.userLoginService.accessToken
        });
        const options = new RequestOptions({ headers: headers});
        return this.http.put(`http://localhost:8080/user/${userId}`, {
            userDetail
        }, options)
        .pipe(
            map((resp: any) => {
                console.log(resp);

                return true;
            }));
    }

    public get userRoles(): any {
        return this.roleArry.slice();
    }


    public activate(userId: string): Observable<boolean> {

        if (!this.userLoginService.isLoggedIn) {
            console.log('UserService.update: user not logged in');
            return null;
        }

        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json',
            'access-token': this.userLoginService.accessToken
        });
        const options = new RequestOptions({ headers: headers});
        return this.http.put(`http://localhost:8080/user/${userId}/activate`, options)
        .pipe(
            map((resp: any) => {
                console.log(resp);

                return true;
            }));
    }

    public deactivate(userId: string): Observable<boolean> {

        if (!this.userLoginService.isLoggedIn) {
            console.log('UserService.update: user not logged in');
            return null;
        }

        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json',
            'access-token': this.userLoginService.accessToken
        });
        const options = new RequestOptions({ headers: headers});
        return this.http.put(`http://localhost:8080/user/deactivate/${userId}`, {userDetail: {}}, options)
        .pipe(
            map((resp: any) => {
                console.log(resp);

                return true;
            }));
    }
    public resetpassword(userId: string): Observable<boolean> {

        if (!this.userLoginService.isLoggedIn) {
            console.log('UserService.update: user not logged in');
            return null;
        }

        // Add in JSON header and access token header
        const headers = new Headers({
            'Content-Type': 'application/Json',
            'access-token': this.userLoginService.accessToken
        });
        const options = new RequestOptions({ headers: headers});
        return this.http.put(`http://localhost:8080/user/${userId}/resetpass`, options)
        .pipe(
            map((resp: any) => {
                console.log(resp);

                return true;
            }));
    }

    private handleError(err: any): Observable<User> {
        //
        console.log('create user failed: err =', err);
        return of(null);
    }
}
import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions} from '@angular/Http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class PasswordService {

    constructor(private http: Http) {}

    resetPasswordByTokenID(tokenId: string, newPassword: string): Observable<boolean> {
        // create a header
        const headers = new Headers({
            'Content-Type': 'application/json'
        });
        const options = new RequestOptions({headers: headers});

        return this.http.put('/api/password', {
            resetId: tokenId,
            newPassword: newPassword
        }, options).pipe(map((resp: any) => {

            // test is the response is there, and then test that the response body is there,
            //  and then make sure the response body contains a key 'status', and the value is 'Success'
            //  if so return true, otherwise return false.
            if (resp) {
                const respBody = resp.json;

                if (respBody.status === 'Success') {
                    return true;
                }
            }
            return false;
        }));
    }
}

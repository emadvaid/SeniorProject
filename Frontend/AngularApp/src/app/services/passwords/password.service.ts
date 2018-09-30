import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class PasswordService {

    resetPasswordByTokenID(tokenId: string, newPassword: string): Observable<boolean> {
        //
    
    }
}

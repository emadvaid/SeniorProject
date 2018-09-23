import {Injectable } from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot} from '@angular/router';
import {UserLoginService} from '../user.login/user.login.service';

@Injectable()
export class UserTypeAuthGuardService implements CanActivate {

    constructor(public userService: UserLoginService, public router: Router) {}

    canActivate(route: ActivatedRouteSnapshot): boolean {

        const expectedUserType = route.data.expectedUserType;

        if (this.userService.getUserType !== expectedUserType) {
            this.router.navigate(['error-page']);
            return false;
        }

        return true;
    }
}

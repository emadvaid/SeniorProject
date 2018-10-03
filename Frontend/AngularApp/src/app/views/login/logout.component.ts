import { Component, OnInit } from '@angular/core';
import { UserLoginService } from '../../services/user.login/user.login.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-logout',
    template: '<h1>Goodbye</h1>'
})
export class LogoutComponent implements OnInit {

    constructor(private userloginService: UserLoginService, private router: Router) {}
    //
    ngOnInit() {
        this.userloginService.logout();
        this.router.navigate(['']);
    }
}

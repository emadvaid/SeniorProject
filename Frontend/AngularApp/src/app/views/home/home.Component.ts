import {Component, OnInit, OnChanges} from '@angular/core';
import { UserLoginService } from '../../services/user.login/user.login.service';
import {UserTypes} from '../../models/User';
import { Router } from '@angular/router';

@Component({
    selector: 'app-home',
    template: `
        <app-login-form></app-login-form>
    `
})
export class HomeComponent {
}

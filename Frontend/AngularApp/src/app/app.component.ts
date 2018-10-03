import { Component, OnInit, OnChanges } from '@angular/core';
import { UserLoginService } from './services/user.login/user.login.service';
import { UserTypes } from './models/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  title = 'Translation Verificaiton ';
  loggedIn = false;
  isAdmin = false;

  constructor(private userLoginService: UserLoginService, private router: Router) {
  }

  ngOnInit() {
    this.loggedIn = this.userLoginService.isLoggedIn;
    this.isAdmin = this.userLoginService.isUserType(UserTypes.admin);
    this.router.events.subscribe((val) => {
      this.loggedIn = this.userLoginService.isLoggedIn;
      this.isAdmin = this.userLoginService.isUserType(UserTypes.admin);
      console.log('AppComponent: router changed event detected.');
    });
  }

  get diagnostics() {
    return (
      'loggedIn = ' + this.loggedIn
      + ', isAdmin = ' + this.isAdmin
    );
  }
}

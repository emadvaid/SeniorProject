import { Component, OnInit, OnChanges } from '@angular/core';
import { UserLoginService } from './services/user.login/user.login.service';
import { UserTypes } from './models/User';
import { Router } from '@angular/router';
<<<<<<< HEAD
import {LocalStorageService, SessionStorageService} from 'ngx-webstorage';
=======
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
>>>>>>> master

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  title = 'Translation Verification ';
  loggedIn = false;
  isAdmin = false;

  constructor(private userLoginService: UserLoginService,
<<<<<<< HEAD
              private router: Router,
              private storage: SessionStorageService) {
=======
    private router: Router,
     private _cookieService: CookieService,
    // private localSt: LocalStorageService,
    // private sessionSt: SessionStorageService,
    ) {
>>>>>>> master
  }

  ngOnInit() {
    this.loggedIn = this.userLoginService.isLoggedIn;
    this.isAdmin = this.userLoginService.isUserType(UserTypes.admin);
    this.router.events.subscribe((val) => {
      this.loggedIn = this.userLoginService.isLoggedIn;
      this.isAdmin = this.userLoginService.isUserType(UserTypes.admin);
    });
  }

  get diagnostics() {
    return (
      'loggedIn = ' + this.loggedIn
      + ', isAdmin = ' + this.isAdmin
    );
  }

<<<<<<< HEAD
  setSession(id, userName, userRole) {
    this.storage.store('userName', userName);
    this.storage.store('id', id);
    this.storage.store('userRole', userRole);
  }
  getUserName(){
    return this.storage.retrieve('userName');
  }
  getRole() {
    return this.storage.retrieve('userRole');
  }
    delSession(){
      this.storage.clear('userName');
      this.storage.clear('id');
      this.storage.clear('userRole');
    }
=======
  setCookies(){
    this._cookieService.put('username', "test");
}
getCookie(){
    alert(this._cookieService.get('username'));
}
delCookies(){
    this._cookieService.remove('username');
}

  // setSession(userName) {
  //   this.sessionSt.store('username', userName);
  // }
  // getSession() {
  //   alert(this.sessionSt.retrieve('logged-in'));
  // }
  // getUserName() {
  //   return this.sessionSt.retrieve('userName');
  // }

  // delSession() {
  //   this.sessionSt.clear('userName');
  // }
>>>>>>> master
}

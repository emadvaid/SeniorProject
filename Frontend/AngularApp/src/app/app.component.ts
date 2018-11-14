import { Component, OnInit, OnChanges } from '@angular/core';
import { UserLoginService } from './services/user.login/user.login.service';
import { UserTypes } from './models/User';
import { Router } from '@angular/router';
import {LocalStorageService, SessionStorageService} from 'ngx-webstorage';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  title = 'Translation Verification ';
  loggedIn = false;
  isAdmin = false;

  constructor(private userLoginService: UserLoginService,
              private router: Router,
              private storage: SessionStorageService) {
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
}

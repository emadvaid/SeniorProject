import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserLoginService } from '../../services/user.login/user.login.service';
import { User, UserTypes } from '../../models/User';
import { AppComponent } from '../../app.component'; 
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { FlashMessagesService } from 'angular2-flash-messages';

const REMEMBERED_USERNAME = 'remembered_username';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  model: any = {};
  submitted = false;
  private returnUrl: string;

  constructor(private router: Router,
    private userLoginService: UserLoginService,
    private comp: AppComponent,
    private cookies: CookieService,
    private flashMessage: FlashMessagesService
    ) { }

  ngOnInit() {
    // first log out of the current session
    this.userLoginService.logout();

    // set the return url, in the future this will come from the route
    this.returnUrl = '';

    // reset the form values
    this.model.username = localStorage.getItem(REMEMBERED_USERNAME) || '';
    this.model.password = '';
    if (this.model.username) {
      this.model.remember = true;
    }
  }

  onSubmit() {
    // set the sumbitted flag to true
    this.submitted = true;

    //Set username in cookies
    this.cookies.put('username', this.model.username);

    this.userLoginService.authenticateWithUsernamePassword(this.model.username, this.model.password)
      .subscribe(
        (user: User) => {

          // main path check to make sure we now have a valid user
          if (user) {
            // login was successful
            console.log('Login success, userType = ', this.userLoginService.getUserType);

            // make sure to remember the username if selected
            if (this.model.remember) {
              localStorage.setItem(REMEMBERED_USERNAME, this.model.username);
            } else {
              localStorage.removeItem(REMEMBERED_USERNAME);
            }

            // now navigate to the the correct route
            switch (this.userLoginService.getUserType) {
              case UserTypes.admin:
                this.router.navigate(['admin/statistics']);
                break;
              case UserTypes.dealer:
                this.router.navigate(['dealer/keyView']);
                break;
              default:
                this.router.navigate(['error-page']);
            }

            return;

          } else {
            // unsuccessfull login path
            localStorage.removeItem(REMEMBERED_USERNAME);
            console.log('Login failed');
              // display a flash message
            this.flashMessage.show('Login failed', {cssClass: 'alert alert-danger', timeout: 5000});
          }
        },
        (err: any) => {
          // oop caught an error
          localStorage.removeItem(REMEMBERED_USERNAME);
          console.log('Login failed with error', err);
        },
        () => {
          // completion path (cleanup)
          this.submitted = false;
          this.model.username = '';
          this.model.password = '';
          console.log('Login process complete');
        }
      );
  }

  get diagnostics() {
    return 'model = ' + JSON.stringify(this.model)
      + ', submitted = ' + this.submitted
      + ', returnUrl = ' + this.returnUrl;
  }
}

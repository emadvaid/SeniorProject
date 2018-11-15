import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserLoginService } from '../../services/user.login/user.login.service';
import { User, UserTypes } from '../../models/User';
<<<<<<< HEAD
import { AppComponent } from '../../app.component';
=======
import { AppComponent } from '../../app.component'; 
>>>>>>> master

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

<<<<<<< HEAD
  constructor(private router: Router, private userLoginService: UserLoginService,
              private comp: AppComponent) {}
=======
  constructor(private router: Router,
    private userLoginService: UserLoginService,
    private comp: AppComponent, 
    ) { }
>>>>>>> master

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

    this.userLoginService.authenticateWithUsernamePassword(this.model.username, this.model.password)
      .subscribe(
        (user: User) => {

          // main path check to make sure we now have a valid user
          if (user) {
            // login was successful
            console.log('Login success, userType = ', this.userLoginService.getUserType);
            //set session storage
            this.comp.setSession(user.id, user.username, user.typeAsStr);

            //Set username in cookies
            //this.comp.setSession(this.model.username)
            //this.comp.setCookies();
            
            // make sure to remember the username if selected
            if (this.model.remember) {
              localStorage.setItem(REMEMBERED_USERNAME, this.model.username);
            } else {
              localStorage.removeItem(REMEMBERED_USERNAME);
            }

            // now navigate to the the correct route
            switch (this.userLoginService.getUserType) {
              case UserTypes.admin:
                this.router.navigate(['admin']);
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


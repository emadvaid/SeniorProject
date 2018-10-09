import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { LanguagesService } from '../../../../services/languages/languages.service';
import { UserService } from '../../../../services/users/user.service';
import { User } from '../../../../models/User';
import { error } from '@angular/compiler/src/util';
import { ValidatorFn, AbstractControl } from '@angular/forms';
import { ForbiddenNameValidatorDirective } from '../../../../validators/user-validator.validator';

@Component({
    selector: 'app-edit-users',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
    model: any;
    submitted: boolean;
    langArry: any;
    roleArry: any;
    userId: string;

    constructor(private languageService: LanguagesService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute,
        private router: Router) {}

    ngOnInit() {
        this.model = {user: {}};
        this.submitted = false;
        this.langArry = this.languageService.languages;
        this.roleArry = this.userService.userRoles;

        // get the userId from the parameter list of the activated route
        this.activatedRoute.queryParams.subscribe(
            (params: Params) => {

                console.log('EditUserComponent.ngOnInit(): queryParams: ', params);
                this.userId = params['userId'];

                // now check to see that we have a valid user id
                if (this.userService.validateUserId(this.userId)) {
                    // get the User object for this user id, using the UserService
                    // and add to the model
                    this.userService.getById(this.userId).subscribe(
                        (user: User) => {
                            // make sure the user object is not null
                            if (user != null) {
                                // add the user object to model as user
                                this.model.user = user;
                            } else {
                                // log the error, and redirect to the manage users page
                                console.log('EditUserComponent: no user found for userId = ', this.userId);
                                this.router.navigate(['/admin/manageUsers']);
                            }
                        },
                        (err: any) => {
                            // console log the error, and redirect back to manage user page
                                console.log('EditUserComponent: error trying to get User for userId = ', this.userId, err);
                                this.router.navigate(['/admin/manageUsers']);
                        }
                    );
                } else {
                    console.log('EditUserComponent: invalid userId = ', this.userId);
                    this.router.navigate(['/admin/manageUsers']);
                }
            }
        );
    }

    onSubmit(): void {

         // validate
         if (!this.validate()) {
            console.log('onSubmit: validation error');
            return;
        }
        // call service method
        this.submitted = true;

        const updatedUserDetails: User = new User();
        updatedUserDetails.id = this.userId;

        updatedUserDetails.username = this.model.user.username;
        updatedUserDetails.firstName = this.model.user.firstName;
        updatedUserDetails.lastName = this.model.user.lastName;
        updatedUserDetails.email = this.model.user.email;
        updatedUserDetails.language1 = this.model.user.language1;
        updatedUserDetails.language2 = this.model.user.language2;
        updatedUserDetails.isActive = this.model.user.active;
        updatedUserDetails.typeAsStr = this.model.user.typeAsStr;

        console.log('onSubmit(): newUser = ', updatedUserDetails);

        this.userService.update(this.userId, updatedUserDetails)
            .subscribe((result: boolean) => {
                // succesfully created a new user so redirect to the manage user page
                this.router.navigate(['/admin/manageUsers']);
            });

    }

    validate(): boolean {
        return (
           !this.isEmpty(this.model.user.username)
           && !this.isEmpty(this.model.user.language1)
           && !this.isEmpty(this.model.user.typeAsStr)
           && (this.model.user.typeAsStr.toLowerCase() === 'admin' || this.model.user.typeAsStr.toLowerCase() === 'dealer')
        );

    }

    isEmpty(str: string): boolean {
        if (str && typeof(str) === 'string') {
            if (str.length > 0) {
                return false;
            }
        }

        return true;
    }

    get diagnostics() {
      return 'model = ' + JSON.stringify(this.model)
        + ', submitted = ' + this.submitted;
    }
}

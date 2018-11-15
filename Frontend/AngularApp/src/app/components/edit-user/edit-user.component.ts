import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { LanguagesService } from '../../services/languages/languages.service';
import { UserService } from '../../services/users/user.service';
import { User } from '../../models/User';
import { Language } from '../../models/Language';


@Component({
    selector: 'app-edit-users-comp',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
    model: any;
    submitted: boolean;
    roleArry: any;
    userId: string;
    @Output() save: EventEmitter<any> = new EventEmitter();
    @Output() cancel: EventEmitter<any> = new EventEmitter();

    constructor(private languageService: LanguagesService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute,
        private router: Router) {}

    ngOnInit() {
        this.model = {user: {}};
        this.submitted = false;
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

                                // Now that we have the user get all the languages,
                                //   this user can see
                                this.languageService.getAll().subscribe(
                                    (languages: Array<Language>) => {
                                        console.log(languages);
                                        this.model.languages = languages;

                                        // set the checked status of each language
                                        this.model.languages.forEach((lang => {
                                            // search for a matching entry inside the user
                                            const match = user.languages.find(
                                                (userLang) => (lang.id === userLang.id)
                                            );

                                            // set the check status based on whether this lang was found
                                            if (match && match !== null) {
                                                lang.checked = true;
                                            } else {
                                                lang.checked = false;
                                            }
                                        }));
                                    },
                                    (err: any) => {
                                         console.log('ManageLanguageComponent: error getting languages', err);
                                    }
                                );
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
        updatedUserDetails.isActive = this.model.user.active;
        updatedUserDetails.typeAsStr = this.model.user.typeAsStr;
        updatedUserDetails.languages = [];
        this.model.languages.forEach((element) => {
            if (element.checked) {
                updatedUserDetails.languages.push(new Language(element));
            }
        });

        console.log('onSubmit(): newUser = ', updatedUserDetails);

        this.userService.update(this.userId, updatedUserDetails)
            .subscribe((result: boolean) => {
                // succesfully created a new user so redirect to the manage user page
                this.save.emit('saved');
            });

    }

    onCancel() {
        //
        this.cancel.emit('canceled');
    }


    validate(): boolean {
        return (
           !this.isEmpty(this.model.user.username)
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

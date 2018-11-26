import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { UserService } from '../../services/users/user.service';
import { User } from '../../models/User';
import { LanguagesService } from '../../services/languages/languages.service';
import { Language } from 'src/app/models/Language';

@Component({
    selector: 'app-create-user-comp',
    templateUrl: './create-user.component.html',
    styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
    model: any;
    submitted: boolean;
    roleArry: any;
    @Output() save: EventEmitter<any> = new EventEmitter();
    @Output() cancel: EventEmitter<any> = new EventEmitter();

    constructor(
        private languageService: LanguagesService,
        private userService: UserService) {}

    ngOnInit() {
        this.model = {};
        this.submitted = false;
        this.roleArry = this.userService.userRoles;
        this.model.role = 'dealer';
        this.languageService.getAll().subscribe(
            (languages: Array<Language>) => {
                console.log(languages);
                this.model.languages = languages;
                this.model.languages.forEach(element => {
                    element.checked = false;
                });
            },
            (err: any) => {
                 console.log('ManageLanguageComponent: error getting languages', err);
            }
        );
        // just for dev purposes
        // const rnd = Math.floor(10000 * Math.random());
        // this.model.username = 'testusername' + rnd;
        // this.model.firstName = 'first' + rnd;
        // this.model.lastName = 'last' + rnd;
        // this.model.email = 'testusername' + rnd + '@test.com';
    }

    onSubmit(): void {
        // validate
        if (!this.validate()) {
            console.log('onSubmit: validation error');
            return;
        }
        // call service method
        this.submitted = true;

        const newUser: User = new User();
        newUser.username = this.model.username;
        newUser.firstName = this.model.firstName;
        newUser.lastName = this.model.lastName;
        newUser.email = this.model.email;
        newUser.isActive = this.model.active;
        newUser.typeAsStr = this.model.role;
        newUser.languages = [];

        this.model.languages.forEach((element) => {
            if (element.checked) {
                newUser.languages.push(new Language(element));
            }
        });

        console.log('onSubmit(): newUser = ', newUser);

        this.userService.create(newUser)
            .subscribe((user: User) => {
                // succesfully created a new user so redirect to the manage user page
                this.save.emit(`Created new User for ${newUser.username}`);
            });

    }

    onCancel() {
        //
        this.cancel.emit('canceled');
    }

    validate(): boolean {
        console.log('this.model.username', this.model.username,
            'this.isEmpty (this.model.username)', this.isEmpty (this.model.username));
        console.log('this.model.role', this.model.role,
            'this.isEmpty (this.model.role)', this.isEmpty (this.model.role));

        return (
            !this.isEmpty (this.model.username)
            && !this.isEmpty(this.model.role)
            && (this.model.role === 'admin' || this.model.role === 'dealer')
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

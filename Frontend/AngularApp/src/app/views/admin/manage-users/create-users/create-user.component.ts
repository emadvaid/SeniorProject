import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../../../services/users/user.service';
import { User, UserTypes } from 'Translation-Verification-Tool/Frontend/AngularApp/src/app/models/User';

@Component({
    selector: 'app-create-users',
    templateUrl: './create-user.component.html',
    styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
    model: any;
    submitted = false;
    langArry: any;
    roleArry: any;

    constructor(private router: Router, private userService: UserService) {}

    ngOnInit() {
        this.model = {};
        this.langArry = [
            {name: 'Brazillian Portugues'},
            {name: 'English'},
            {name: 'Canadian French'},
            {name: 'German'},
            {name: 'Italian'},
            {name: 'Japanese'},
            {name: 'English'},
            {name: 'Korean'},
            {name: 'Russian'},
            {name: 'Spanish'},
            {name: 'Swedish'},
            {name: 'Simplified Chinese'},
            {name: 'Traditional Chinese'},
            {name: 'Thai'},
            {name: 'Vietnamese'},
        ];
        this.roleArry = [
            {name: 'Admin'},
            {name: 'Dealer'}
        ];
        this.model.role = 'Dealer';
    }

    onSubmit(): void {
        // validate
        if (!this.validate()) {
            console.log('onSubmit: validation error');
            return;
        }
        // call service method
        this.submitted = true;

        const newUser = new User();
        newUser.username = this.model.username;
        newUser.firstName = this.model.firstName;
        newUser.lastName = this.model.lastName;
        newUser.email = this.model.email;
        newUser.language1 = this.model.language1;
        newUser.language2 = this.model.language2;
        newUser.isActive = this.model.active;

        // map the type
        switch (this.model.type) {
            case 'Admin':
                newUser.type = UserTypes.admin;
                break;
            case 'Dealer':
                newUser.type = UserTypes.dealer;
                break;
            default:
                newUser.type = UserTypes.unknown;
        }

        this.userService.createUser(newUser)
            .subscribe((user: User) => {
                // succesfully created a new user so redirect to the manage user page
                this.router.navigate(['/admin/manageUsers']);
            });

    }

    validate(): boolean {
        console.log('this.model.username', this.model.username,
            'this.isEmpty (this.model.username)', this.isEmpty (this.model.username));
        console.log('this.model.language1', this.model.language1,
            'this.isEmpty (this.model.language1)', this.isEmpty (this.model.language1));
        console.log('this.model.role', this.model.role,
            'this.isEmpty (this.model.role)', this.isEmpty (this.model.role));

        return (
            !this.isEmpty (this.model.username)
            && !this.isEmpty(this.model.language1)
            && !this.isEmpty(this.model.role)
            && (this.model.role === 'Admin' || this.model.role === 'Dealer')
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

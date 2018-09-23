import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../../../services/users/user.service';

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
        this.model = {};
        this.roleArry = [
            {name: 'Admin'},
            {name: 'Dealer'}
        ];
    }

    onSubmit() {
        // validate
        // call service method

    }

    validate(): boolean {
        return (
            !this.isEmpty (this.model.username)
            && !this.isEmpty(this.model.language1.name)
            && !this.isEmpty(this.model.language2.name)
            && !this.isEmpty(this.model.active)
            && !this.isEmpty(this.model.role)
            && (this.model.role === 'Admin' || this.model.role === 'Dealer')
        );
    }

    isEmpty(str: string): boolean {
        if (str && typeof(str) === 'string') {
            if (str.length > 0) {
                return true;
            }
        }

        return false;
    }

    get diagnostics() {
      return 'model = ' + JSON.stringify(this.model)
        + ', submitted = ' + this.submitted;
    }
}

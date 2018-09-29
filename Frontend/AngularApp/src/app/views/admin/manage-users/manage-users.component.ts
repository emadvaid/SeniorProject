import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/Http';
import { error } from 'util';
import { Router } from '@angular/router';

import { UserService } from '../../../services/users/user.service';
import { User } from '../../../models/User';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {
    model: any = { };

    // Inject the http object into our class, so we can use it in our Ajax call
    constructor(private userService: UserService, private http: Http, private router: Router) {}

    // initialize this component by making an Ajax call to our dealer profile service
    //  to get the deal list
    ngOnInit() {
        this.userService.getAllUsers().subscribe(
            (users: Array<User>) => {
                this.model.users = users;
            },
            (err: any) => {
                console.log('ManageUsersComponent: error getting users', err);
            }
        );
    }


    createUser() {
        this.router.navigate(['admin/createUser']);
    }
}



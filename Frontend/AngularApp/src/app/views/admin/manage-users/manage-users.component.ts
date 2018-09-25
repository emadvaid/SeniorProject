import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/Http';
import { error } from 'util';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {
    model: any = { };

    // Inject the http object into our class, so we can use it in our Ajax call
    constructor(private http: Http, private router: Router) {}

    // initialize this component by making an Ajax call to our dealer profile service
    //  to get the deal list
    ngOnInit() {
        this.http.get('http://localhost:8080/users')
            .subscribe(
                resp => {
                    console.log('ManageUsersComponent ajax response: ', resp);
                    if (resp) {
                        const respBody = resp.json();
                        console.log('ManageUsersComponent loaded user list: ', respBody);
                        if (respBody.users) {
                            this.model.users = respBody.users;
                        }
                    }

                },
                err => {
                    console.log('ManageUsersComponent error loading dealer list: ', err);
                }
            );
    }


    createUser() {
        this.router.navigate(['admin/createUser']);
    }

    get diagnostics() {
        return 'model = ' + JSON.stringify(this.model);
      }
}



import { Component, OnInit } from '@angular/core';

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

    get diagnostics() {
      return 'model = ' + JSON.stringify(this.model)
        + ', submitted = ' + this.submitted;
    }
}

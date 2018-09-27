import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-edit-users',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
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
            {name: 'admin'},
            {name: 'dealer'}
        ];
    }

    get diagnostics() {
      return 'model = ' + JSON.stringify(this.model)
        + ', submitted = ' + this.submitted;
    }
}

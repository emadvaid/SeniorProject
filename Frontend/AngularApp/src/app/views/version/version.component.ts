import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/Http';
import { error } from 'util';
import { Router } from '@angular/router';

import {VersionService} from '../../services/versions/versions.service';
import { Version } from 'src/app/models/Version';


@Component({
    selector: 'app-version',
    templateUrl: './version.component.html',
    styleUrls: ['./version.component.css']
})
export class VersionComponent implements OnInit {
    model: any = { };
    constructor(private versionService: VersionService, private http: Http, private router: Router) {}

    public ngOnInit() {
        this.refresh();
    }
    private refresh(): void {
        this.versionService.getAll().subscribe(
            (version: Array<Version>) => {
                this.model.versionNum = version;
            },
            (err: any) => {
                console.log('VersionComponent: error getting users', err);
            }
        );
    }
    createVersion() {
        this.router.navigate(['admin/createVersion']);
    }
    get diagnostics() {
        return 'model = ' + JSON.stringify(this.model);
    }
}

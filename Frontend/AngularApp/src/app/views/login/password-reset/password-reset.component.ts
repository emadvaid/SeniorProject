import { Component, OnInit } from '@angular/core';
import { PasswordService } from '../../../services/passwords/password.service';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
    selector: 'app-reset-pass',
    templateUrl: './password-reset.component.html',
    styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {
    model: any = {};
    submitted = false;
    tokenId: string;
    newUser = false;

    constructor(private passService: PasswordService, private router: Router,
        private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            (params: Params) => {
                this.tokenId = params['resetId'];
                this.newUser = params['newUser'] || false;
            }
        );
    }

    onSubmit() {
        // make sure the new password and the password confirm match,
        if (this.model.newPassword !== this.model.confirmPassword) {
            console.log('Error passwords do not match');
            return;
        }
        //   and meet the minimum password requirements
        if (!this.model.newPassword || this.model.password.length < 8) {
            console.log('bad password format');
            return;
        }

        // then submit
        this.submitted = true;

        this.passService.resetPasswordByTokenID(this.tokenId, this.model.newPassword)
            .subscribe(
                (result: boolean) => {
                    //
                    console.log('Succesfully changed password.');
                    this.router.navigate(['/login']);
                },
                (err: any) => {
                    //
                }
            );


    }

    get diagnostics() {
        return 'model = ' + JSON.stringify(this.model)
          + ', submitted = ' + this.submitted;
      }
}


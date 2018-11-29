import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/Http';
import { Router } from '@angular/router';

import { UserService } from '../../../services/users/user.service';
import { User, UserTypes } from '../../../models/User';
import { Language } from 'src/app/models/Language';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateUserModalComponent } from 'src/app/components/create-user-modal/create-user-modal.component';
import { EditUserModalComponent } from 'src/app/components/edit-user-modal/edit-user-modal.component';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {
    model: any = { };

    // Inject the http object into our class, so we can use it in our Ajax call
    constructor(private modalService: NgbModal,

         private userService: UserService,
         private http: Http,
         private router: Router) {}

    // initialize this component by making an Ajax call to our dealer profile service
    //  to get the deal list
    public ngOnInit() {
        this.refresh();
    }

    private refresh(): void {
        this.userService.getAll().subscribe(
            (users: Array<User>) => {
                this.model.users = users;
            },
            (err: any) => {
                console.log('ManageUsersComponent: error getting users', err);
            }
        );
    }

    /*
     *  This method concats the languages toget as a string ie
     *    "en, es, fr"
     */
    concatLangs(user: User): string {
        if (user.typeAsStr === 'admin') {
            return 'All Languages';
        }
        // Do the work here
        const results = [];

        const langArray: Array<Language> = user.languages;

        langArray.forEach((lang: Language) => {
            results.push(lang.langCode);
        });

        return results.join();
    }


    createUser() {
         // popup the modal
      const modalRef = this.modalService.open(CreateUserModalComponent);

      modalRef.result
        .then(
            (res) => {

                console.log('ManageUserComponent.createUser: modal returned ', res);
                this.refresh();
                alert(res);
        })
        .catch(
            err => {

                console.log('ManageUserComponent.createUser(): modal error: ', err);
                this.refresh();
                alert(err);
            }
        );
    }

    editUser(event: any) {
        console.log('editUser: event.target.dataset[\'userid\'] = ', event.target.dataset['userid']);
        const modalRef = this.modalService.open(EditUserModalComponent);
        modalRef.componentInstance.userId = event.target.dataset['userid'];

        modalRef.result
          .then(
              (res) => {
                  console.log('ManageUserComponent.createUser: modal returned ', res);
                  this.refresh();
                  alert(res);
          })
          .catch(
              err => {
                  console.log('ManageUserComponent.createUser(): modal error: ', err);
                  this.refresh();
                  alert(err);
              }
          );
    }

    activateUser(event: any) {
        this.userService.activate(event.target.dataset['userid'])
        .subscribe(
            (status: boolean) => {
                if (status) {
                    // happy path refresh page (in future add a success flash message)
                    this.refresh();
                } else {
                    // unhappy path do something else, refresh
                }
            }
        );
    }

    deactivateUser(event: any) {
        this.userService.deactivate(event.target.dataset['userid'])
        .subscribe(
            (status: boolean) => {
                if (status) {
                    // happy path refresh page (in future add a success flash message)
                    this.refresh();
                } else {
                    // unhappy path do something else, refresh
                }
            }
        );
    }

    resetPassword(event: any) {
        this.userService.resetpassword(event.target.dataset['userid'])
        .subscribe(
            (status: boolean) => {
                if (status) {
                    // happy path refresh page (in future add a success flash message)
                    this.refresh();
                } else {
                    // unhappy path do something else, refresh
                }
            }
        );
    }
}


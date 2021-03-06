import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ModalComponent } from '../modal/modal.component';

@Component({
    selector: 'app-create-user-modal',
    template: `
        <app-modal title={{title}}>
          <app-create-user-comp
              (save)="onSave($event)"
              (cancel)="onCancel($event)"
          ></app-create-user-comp>
        </app-modal>
    `,
    styles: []
})
export class CreateUserModalComponent implements OnInit, AfterViewInit {
    title = 'Create User';


    @ViewChild(ModalComponent) activeModal: ModalComponent;

    ngOnInit() {

    }

    ngAfterViewInit() {
        console.log();
    }

    onSave(msg) {
        //
        console.log();
        this.activeModal.activeModal.close(msg);
    }

    onCancel(msg) {
        //
        console.log();
        this.activeModal.activeModal.close(msg);
    }
}

import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ModalComponent } from '../modal/modal.component';

@Component({
    selector: 'app-create-user-modal',
    template: `
        <app-modal title={{title}}>
          <app-create-user-comp
              (save)="onSave()"
              (cancel)="onCancel()"
          ></app-create-user-comp>
        </app-modal>
    `,
    styles: []
})
export class CreateUserModalComponent implements OnInit, AfterViewInit {
    title = '';
    username = '';

    @ViewChild(ModalComponent) activeModal: ModalComponent;

    ngOnInit() {
        this.title = 'Create User';
    }

    ngAfterViewInit() {
        console.log();
    }

    onSave() {
        //
        console.log();
        this.activeModal.activeModal.close('save');
    }

    onCancel() {
        //
        console.log();
        this.activeModal.activeModal.close('cancel');
    }
}

import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ModalComponent } from '../modal/modal.component';

@Component({
    selector: 'app-edit-user-modal',
    template: `
        <app-modal title={{title}}>
          <app-edit-users-comp
              userId={{userId}}
              (save)="onSave($event)"
              (cancel)="onCancel($event)"
          ></app-edit-users-comp>
        </app-modal>
    `,
    styles: []
})
export class EditUserModalComponent implements OnInit, AfterViewInit {
    private title = 'Edit User';
    public userId = '';

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

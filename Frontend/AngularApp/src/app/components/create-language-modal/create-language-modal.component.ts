import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ModalComponent } from '../modal/modal.component';

@Component({
    selector: 'app-create-language-modal',
    template: `
        <app-modal title={{title}}>
          <app-create-language-comp
              (save)="onSave($event)"
              (cancel)="onCancel($event)"
          ></app-create-language-comp>
        </app-modal>
    `,
    styles: []
})
export class CreateLanguageModalComponent implements OnInit, AfterViewInit {
    title = '';
    username = '';

    @ViewChild(ModalComponent) activeModal: ModalComponent;

    ngOnInit() {
        this.title = 'Create Language';
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

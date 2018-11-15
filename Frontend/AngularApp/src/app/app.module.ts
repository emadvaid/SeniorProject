import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { NgbModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';


import { AppRoutingModule, routingComponents } from './app-routing.module';

import { AppComponent } from './app.component';
import { UserLoginService } from './services/user.login/user.login.service';
import { UserTypeAuthGuardService } from './services/auth/authGuard.service';
import { HttpModule } from '@angular/Http';
import { FileFunctionsComponent } from './views/file-functions/file-functions.component';
import { FileSelectDirective} from 'ng2-file-upload';
import { FileFunctionsRedoComponent } from './views/file-functions-redo/file-functions-redo.component';
import { HttpClientModule} from '@angular/common/http';
import { FileDropModule} from 'ngx-file-drop';
import { KeyViewComponent } from './views/key-view/key-view.component';
import { CreateUserModalComponent } from './components/create-user-modal/create-user-modal.component';
import { ModalComponent } from './components/modal/modal.component';
import { CreateUserComponent } from './components/create-user/create-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { EditUserModalComponent } from './components/edit-user-modal/edit-user-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    FileSelectDirective,
    FileFunctionsComponent,
    FileFunctionsRedoComponent,
    KeyViewComponent,
    ModalComponent,
    CreateUserComponent,
    CreateUserModalComponent,
    EditUserComponent,
    EditUserModalComponent

  ],
  imports: [
    HttpModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    FileDropModule,
    NgbModule.forRoot()
  ],
  providers: [
    NgbActiveModal,
    UserLoginService,
    UserTypeAuthGuardService
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    CreateUserModalComponent,
    EditUserModalComponent
  ]
})
export class AppModule { }

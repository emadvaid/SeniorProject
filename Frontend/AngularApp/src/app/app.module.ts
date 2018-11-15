import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';


import { AppRoutingModule, routingComponents } from './app-routing.module';

import { AppComponent } from './app.component';
import { UserLoginService } from './services/user.login/user.login.service';
import { UserTypeAuthGuardService } from './services/auth/authGuard.service';
import { FileFunctionsService } from './services/file_functions/file-functions.service';
import { Http, HttpModule } from '@angular/Http';
import { FileFunctionsComponent } from './views/file-functions/file-functions.component';
import { FileSelectDirective} from 'ng2-file-upload';
import {HttpClientModule} from '@angular/common/http';
import {FileDropModule} from 'ngx-file-drop';
import { KeyViewComponent } from './views/key-view/key-view.component';
import { ChartsModule } from 'ng2-charts';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { SharedDataService } from './services/shared-data/shared-data.service';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    FileSelectDirective,
    FileFunctionsComponent,
    KeyViewComponent
  ],
  imports: [
    HttpModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    FileDropModule,
    ChartsModule,
    NgxWebstorageModule.forRoot()
  ],
  providers: [
    UserLoginService,
    UserTypeAuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

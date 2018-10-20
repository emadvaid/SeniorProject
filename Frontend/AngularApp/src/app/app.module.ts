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
import { FileFunctionsRedoComponent } from './views/file-functions-redo/file-functions-redo.component';
import {HttpClientModule} from '@angular/common/http';
import {FileDropModule} from 'ngx-file-drop';
import { StatisticsComponent } from './views/admin/statistics/statistics.component';
import { ExportFilesComponent } from './views/admin/export-files/export-files.component';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    FileSelectDirective,
    FileFunctionsComponent,
    FileFunctionsRedoComponent,
    StatisticsComponent,
    ExportFilesComponent,
  ],
  imports: [
    HttpModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    FileDropModule
  ],
  providers: [
    UserLoginService,
    UserTypeAuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';


import { AppRoutingModule, routingComponents } from './app-routing.module';

import { AppComponent } from './app.component';
import { UserLoginService } from './services/user.login/user.login.service';
import { UserTypeAuthGuardService } from './services/auth/authGuard.service';
import { Http, HttpModule } from '@angular/Http';
import { FileFunctionsComponent } from './views/file-functions/file-functions.component';
import { FileSelectDirective} from 'ng2-file-upload';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    FileFunctionsComponent,
    FileSelectDirective
  ],
  imports: [
    HttpModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    UserLoginService,
    UserTypeAuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

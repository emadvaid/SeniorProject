import { NgModule } from '@angular/core';

import {RouterModule, Routes} from '@angular/router';

import {LoginFormComponent} from './views/login/login-form.component';
import { HomeComponent } from './views/home/home.Component';
import { PageNotFoundComponent } from './views/page-not-found/page-not-found.component';
import { DealerDashboardComponent } from './views/dealer/dealer.dash.component';
import { AdminDashboardComponent } from './views/admin/admin.dash.component';
import { ErrorPageComponent } from './views/error-page/error-page.component';
import { UserTypes } from './models/User';
import { UserTypeAuthGuardService } from './services/auth/authGuard.service';
import { ManageUsersComponent } from './views/admin/manage-users/manage-users.component';
import { FileFunctionsComponent } from './views/file-functions/file-functions.component';
import { PasswordResetComponent } from './views/login/password-reset/password-reset.component';
import { LogoutComponent } from './views/login/logout.component';
import { ManageLanguageComponent } from './views/admin/manage-language/manage-language.component';
import { ManageVersionComponent } from './views/admin/manage-language/manage-versions/manage-versions.component';
import { VersionComponent } from './views/version/version.component';
import { StatisticsComponent } from './views/admin/statistics/statistics.component';
import { ExportFilesComponent } from './views/admin/export-files/export-files.component';
import { KeyViewComponent } from './views/key-view/key-view.component';
import { LogsComponent } from './views/admin/logs/logs.component';
import { EditUserViewComponent } from './views/admin/manage-users/edit-users-view/edit-users-view.component';
import { CreateLanguageViewComponent } from './views/admin/manage-language/create-language-view/create-language-view.component';




const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginFormComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'resetpass', component: PasswordResetComponent},
  {
    path: 'admin',
    canActivate: [UserTypeAuthGuardService],
    data: {
      expectedUserType: UserTypes.admin
    },
    children: [
      {path: '', component: AdminDashboardComponent},
      {path: 'manageUsers', component: ManageUsersComponent},
      {path: 'manageLanguages' , component: ManageLanguageComponent},
      {path: 'createLanguage' , component: CreateLanguageViewComponent},
      {path: 'manageVersions', component: ManageVersionComponent},
      {path: 'importFile', component: FileFunctionsComponent},
      {path: 'createVersion', component: VersionComponent},
      {path: 'exportFile', component: ExportFilesComponent},
      {path: 'statistics', component: StatisticsComponent},
      {path: 'keyView', component: KeyViewComponent},
      {path: 'logs', component: LogsComponent},
    ]
  },
  {
    path: 'dealer',
    canActivate: [UserTypeAuthGuardService],
    data: {
      expectedUserType: UserTypes.dealer
    },
    children: [
      {path: '', component: DealerDashboardComponent},
      {path: 'keyView', component: KeyViewComponent}
    ]
  },
  {path: 'error-page', component: ErrorPageComponent},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, {enableTracing: false})],
  exports: [RouterModule]
})
export class AppRoutingModule {}

export const routingComponents = [
  LoginFormComponent,
  LogoutComponent,
  HomeComponent,
  PasswordResetComponent,
  FileFunctionsComponent,
  VersionComponent,
  DealerDashboardComponent,
  AdminDashboardComponent,
  ManageUsersComponent,
  EditUserViewComponent,
  ManageLanguageComponent,
  CreateLanguageViewComponent,
  PageNotFoundComponent,
  ErrorPageComponent,
  ExportFilesComponent,
  StatisticsComponent,
  ManageVersionComponent,
  LogsComponent,
];

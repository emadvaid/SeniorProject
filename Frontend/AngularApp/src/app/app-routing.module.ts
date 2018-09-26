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
import { CreateUserComponent } from './views/admin/manage-users/create-users/create-user.component';
import { EditUserComponent } from './views/admin/manage-users/edit-users/edit-user.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginFormComponent},
  {
    path: 'admin',
    canActivate: [UserTypeAuthGuardService],
    data: {
      expectedUserType: UserTypes.admin
    },
    children: [
      {path: '', component: AdminDashboardComponent},
      {path: 'manageUsers', component: ManageUsersComponent},
      {path: 'createUser' , component: CreateUserComponent},
      {path: 'editUser' , component: EditUserComponent},
    ]
  },
  {
    path: 'dealer',
    canActivate: [UserTypeAuthGuardService],
    data: {
      expectedUserType: UserTypes.dealer
    },
    children: [
      {path: '', component: DealerDashboardComponent}
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
  HomeComponent,
  DealerDashboardComponent,
  AdminDashboardComponent,
  ManageUsersComponent,
  CreateUserComponent,
  EditUserComponent,
  PageNotFoundComponent,
  ErrorPageComponent
];

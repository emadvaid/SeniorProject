import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-dash',
  template: `
    <h1>Admin Dashboard Component</h1>
    <nav>
      <ul class="nav-links">
        <li class="nav-link"><a routerLink="/admin/manageUsers" routerLinkActive="active">Manage Users</a></li>
        <li class="nav-link"><a routerLink="/home" routerLinkActive="active">Languages</a></li>
        <li class="nav-link"><a routerLink="/home" routerLinkActive="active">Import/Export XML</a></li>
        <li class="nav-link"><a routerLink="/home" routerLinkActive="active">View Translations</a></li>
      </ul>
    </nav>
  `
})
export class AdminDashboardComponent {
}

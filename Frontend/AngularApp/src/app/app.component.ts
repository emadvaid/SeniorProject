import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <!--The content below is only a placeholder and can be replaced.-->
    <header>
      <!--
      <h1 style="text-align:center">
        {{title}}
      </h1>
      <nav>
        <ul>
          <li><a routerLink="/home" routerLinkActive="active">Home</a></li>
          <li><a routerLink="/login" routerLinkActive="active">Login</a></li>
          <li><a routerLink="/admin-dash" routerLinkActive="false">Admin</a></li>
          <li><a routerLink="/dealer-dash" routerLinkActive="active">Dealer</a></li>
        </ul>
      </nav>
      -->
    </header>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  title = 'Welcome';
}

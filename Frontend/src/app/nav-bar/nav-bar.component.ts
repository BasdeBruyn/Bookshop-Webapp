import { Component, OnInit } from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  constructor(private authService: AuthService) {}

  isAuthorized = false;

  ngOnInit() {
    this.authService.authorized.subscribe(
      (authorized: boolean) => {
        this.isAuthorized = authorized;
      }
    );
  }

}

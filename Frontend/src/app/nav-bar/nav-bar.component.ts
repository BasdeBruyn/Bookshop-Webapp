import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit, OnDestroy {

  constructor(public authService: AuthService) {}

  public isAuthorized;
  public isAdmin;

  public authSubscription: Subscription;
  public adminSubscription: Subscription;

  ngOnInit() {
    this.isAuthorized = this.authService.isAuthorized;
    this.authSubscription = this.authService.authorizedObservable.subscribe(
      (isAuthorized: boolean) => {
        this.isAuthorized = isAuthorized;
      }
    );
    this.isAdmin = this.authService.isAdmin;
    this.adminSubscription = this.authService.adminObservable.subscribe(
      (isAdmin: boolean) => {
        this.isAdmin = isAdmin;
      }
    );
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
    this.adminSubscription.unsubscribe();
  }

}

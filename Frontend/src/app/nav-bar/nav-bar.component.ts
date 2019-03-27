import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit, OnDestroy {

  constructor(private authService: AuthService) {}

  private _isAuthorized;
  private _isAdmin;

  private _authSubscription: Subscription;
  private _adminSubscription: Subscription;

  ngOnInit() {
    this._isAuthorized = this.authService.isAuthorized;
    this._authSubscription = this.authService.authorizedObservable.subscribe(
      (isAuthorized: boolean) => {
        this._isAuthorized = isAuthorized;
      }
    );
    this._isAdmin = this.authService.isAdmin;
    this._adminSubscription = this.authService.adminObservable.subscribe(
      (isAdmin: boolean) => {
        this._isAdmin = isAdmin;
      }
    );
  }

  ngOnDestroy(): void {
    this._authSubscription.unsubscribe();
    this._adminSubscription.unsubscribe();
  }

}

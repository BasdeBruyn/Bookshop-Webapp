import {Injectable, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {User} from './user.model';

@Injectable()
export class AuthService implements OnInit {
  private _authorizedObservable = new Subject<boolean>();
  private _isAuthorized = true;
  private _adminObservable = new Subject<boolean>();
  private _isAdmin = true;
  private _user;

  ngOnInit(): void { }

  login(user: User): boolean {
    this._isAuthorized = true;
    this._authorizedObservable.next(this._isAuthorized);
    this._isAdmin = user.isAdmin;
    this._adminObservable.next(this._isAdmin);
    return false;
  }

  logout() {
    this._isAuthorized = false;
    this._authorizedObservable.next(this._isAuthorized);
    this._isAdmin = false;
    this._adminObservable.next(this._isAdmin);
  }

  get authorizedObservable(): Subject<boolean> { return this._authorizedObservable; }
  get isAuthorized(): boolean { return this._isAuthorized; }
  get isAdmin(): boolean { return this._isAdmin; }
  get adminObservable(): Subject<boolean> { return this._adminObservable; }
  get user() { return this._user; }
}

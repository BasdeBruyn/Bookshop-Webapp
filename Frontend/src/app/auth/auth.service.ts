import {Injectable, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {User} from './user.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Router} from '@angular/router';

@Injectable()
export class AuthService implements OnInit {
  private _authorizedObservable = new Subject<boolean>();
  private _isAuthorized = false;
  private _adminObservable = new Subject<boolean>();
  private _isAdmin = false;
  private _user = new User('bas@ha.co', 'password', true);


  constructor(private httpClient: HttpClient,
              private router: Router) {}

  getAuthHeaders() {
    return  new HttpHeaders()
      .append('Authorization',
        'Basic ' + window.btoa(this._user.email + ':' + this._user.password));
  }

  ngOnInit(): void {}

  login(user: User) {
    this._user = user;
    const httpOptions = {headers: this.getAuthHeaders()};
    const call = this.httpClient.get<User>(environment.server + 'user/authenticate', httpOptions);
    call.subscribe(
      response => {
        this.handleLogin(response);
      },
      error => {
        console.log(error);
      }
    );
  }

  logout() {
    this._isAuthorized = false;
    this._authorizedObservable.next(this._isAuthorized);
    this._isAdmin = false;
    this._adminObservable.next(this._isAdmin);
    this._user = null;
  }

  handleLogin(user: User) {
    this._user.isAdmin = user.isAdmin;
    this._user.id = user.id;
    this._isAuthorized = true;
    this._authorizedObservable.next(this._isAuthorized);
    this._isAdmin = this._user.isAdmin;
    this._adminObservable.next(this._isAdmin);
    this.router.navigate(['']);
  }

  register(user: User) {
    user.isAdmin = false;
    this._user = user;
    const httpOptions = {headers: this.getAuthHeaders()};
    const call = this.httpClient.post<User>(environment.server + 'user', user, httpOptions);
    call.subscribe(
      response => {
        this.handleLogin(response);
      },
      error => {
        console.log(error);
      }
    );
  }

  get authorizedObservable(): Subject<boolean> { return this._authorizedObservable; }
  get isAuthorized(): boolean { return this._isAuthorized; }
  get isAdmin(): boolean { return this._isAdmin; }
  get adminObservable(): Subject<boolean> { return this._adminObservable; }
  get user() { return this._user; }
}

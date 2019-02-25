import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable()
export class AuthService {
  authorized = new Subject<boolean>();

  private isAuthorized = false;

  isUserAuthorized() {
    return this.isAuthorized;
  }

  authorize() {
    this.isAuthorized === true ? this.isAuthorized = false : this.isAuthorized = true;
    this.authorized.next(this.isAuthorized);
  }
}

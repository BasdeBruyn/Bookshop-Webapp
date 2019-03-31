import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../auth.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {NgForm} from '@angular/forms';
import {User} from '../user.model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit, OnDestroy {

  private _loginMode = false;
  private _routeSubscription: Subscription;
  private _passwordShown = false;
  private _duplicateEmail = false;
  private _invalidLogin = false;

  constructor(private authService: AuthService,
              private route: ActivatedRoute,
              private router: Router) {}

  @ViewChild('form') form: NgForm;

  ngOnInit() {
    this._routeSubscription = this.route.params.subscribe(
      (params: Params) => {
        this._loginMode = params['login'] === 'true';
      }
    );
  }

  ngOnDestroy(): void {
    this._routeSubscription.unsubscribe();
  }

  onSubmit() {
    const formData = this.form.value;
    const user = new User(formData.email, formData.password, false);
    if (this._loginMode) {
      this.authService.login(user);
    } else {
      this.authService.register(user);
    }
    setTimeout(() => {
      if (this._loginMode && !this.authService.isAuthorized) {
        this._invalidLogin = true;
      } else if (!this.authService.isAuthorized) {
        this._duplicateEmail = true;
      }
    }, 500);
  }
}

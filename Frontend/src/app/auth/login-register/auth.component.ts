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

  public loginMode = false;
  public routeSubscription: Subscription;
  public passwordShown = false;
  public duplicateEmail = false;
  public invalidLogin = false;

  constructor(private authService: AuthService,
              private route: ActivatedRoute,
              private router: Router) {}

  @ViewChild('form') form: NgForm;

  ngOnInit() {
    this.routeSubscription = this.route.params.subscribe(
      (params: Params) => {
        this.loginMode = params['login'] === 'true';
      }
    );
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
  }

  onSubmit() {
    const formData = this.form.value;
    const user = new User(formData.email, formData.password, false);
    if (this.loginMode) {
      this.authService.login(user);
    } else {
      this.authService.register(user);
    }
    setTimeout(() => {
      if (this.loginMode && !this.authService.isAuthorized) {
        this.invalidLogin = true;
      } else if (!this.authService.isAuthorized) {
        this.duplicateEmail = true;
      }
    }, 500);
  }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {Item} from '../item.model';
import {ActivatedRoute, Params} from '@angular/router';
import {ItemService} from '../item.service';
import {AuthService} from '../../auth/auth.service';
import {CartService} from '../../cart/cart.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-item-display',
  templateUrl: './item-display.component.html',
  styleUrls: ['./item-display.component.scss']
})
export class ItemDisplayComponent implements OnInit, OnDestroy {

  private _item: Item;
  private _isAuthorized;
  private _routeSubscription: Subscription;
  private _authSubscription: Subscription;

  constructor(private route: ActivatedRoute,
              private itemService: ItemService,
              private authService: AuthService,
              private cartService: CartService) { }

  ngOnInit() {
    this._routeSubscription = this.route.params.subscribe(
      (params: Params) => {
          const itemId = +params['id'];
          this._item = this.itemService.items[itemId];
      }
    );
    this._isAuthorized = this.authService.isAuthorized;
    this._authSubscription = this.authService.authorizedObservable.subscribe(
      (isAuthorized: boolean) => {
        this._isAuthorized = isAuthorized;
      }
    );
  }

  ngOnDestroy(): void {
    this._routeSubscription.unsubscribe();
    this._authSubscription.unsubscribe();
  }

}

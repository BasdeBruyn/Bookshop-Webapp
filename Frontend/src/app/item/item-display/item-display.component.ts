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

  constructor(private route: ActivatedRoute,
              private itemService: ItemService,
              private authService: AuthService,
              private cartService: CartService) {
  }
  private _itemId: number;
  private _item: Item;
  private _isAuthorized: boolean;
  private _itemSubscription: Subscription;
  private _routeSubscription: Subscription;
  private _authSubscription: Subscription;

  private _addedToCart = false;
2;
  ngOnInit() {
    this.itemService.getItems();
    this._routeSubscription = this.route.params.subscribe(
      (params: Params) => {
          this._itemId = +params['id'];
          this._item = this.itemService.items[this._itemId];
      }
    );
    this._itemSubscription = this.itemService.itemsObservable.subscribe(
      (items: Item[]) => {
        this._item = items[this._itemId];
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

  addToCart() {
    this.cartService.addToCart(this._item.id);
    this._addedToCart = true;
    setTimeout(() => this._addedToCart = false, 2000);
  }

}

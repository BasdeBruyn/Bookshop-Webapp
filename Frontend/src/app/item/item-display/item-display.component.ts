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

  constructor(public route: ActivatedRoute,
              public itemService: ItemService,
              public authService: AuthService,
              public cartService: CartService) {
  }
  public itemId: number;
  public item: Item;
  public isAuthorized: boolean;
  public itemSubscription: Subscription;
  public routeSubscription: Subscription;
  public authSubscription: Subscription;

  public addedToCart = false;
2;
  ngOnInit() {
    this.itemService.getItems();
    this.routeSubscription = this.route.params.subscribe(
      (params: Params) => {
          this.itemId = +params['id'];
          this.item = this.itemService.items[this.itemId];
      }
    );
    this.itemSubscription = this.itemService.itemsObservable.subscribe(
      (items: Item[]) => {
        this.item = items[this.itemId];
      }
    );
    this.isAuthorized = this.authService.isAuthorized;
    this.authSubscription = this.authService.authorizedObservable.subscribe(
      (isAuthorized: boolean) => {
        this.isAuthorized = isAuthorized;
      }
    );
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
    this.authSubscription.unsubscribe();
  }

  addToCart() {
    this.cartService.addToCart(this.item.id);
    this.addedToCart = true;
    setTimeout(() => this.addedToCart = false, 2000);
  }

}

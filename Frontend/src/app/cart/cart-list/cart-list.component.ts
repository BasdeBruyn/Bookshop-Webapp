import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from '../cart.service';
import {Item} from '../../item/item.model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-cart-list',
  templateUrl: './cart-list.component.html',
  styleUrls: ['./cart-list.component.scss']
})
export class CartListComponent implements OnInit, OnDestroy {

  private _items: Item[];
  private _cartSubscription: Subscription;

  constructor(private cartService: CartService) { }

  ngOnInit() {
    this.cartService.getItems();
    this._items = this.cartService.items;
    this._cartSubscription = this.cartService.itemObservable.subscribe(
      (items: Item[]) => {
        this._items = items;
      }
    );
  }

  ngOnDestroy() {
    this._cartSubscription.unsubscribe();
  }

  onRemove(index: number) {
    this.cartService.removeFromCart(index);
  }
}

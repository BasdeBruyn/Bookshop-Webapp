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

  public items: Item[] = [];
  public cartSubscription: Subscription;

  constructor(public cartService: CartService) { }

  ngOnInit() {
    this.cartService.getItems();
    this.items = this.cartService.items;
    this.cartSubscription = this.cartService.itemObservable.subscribe(
      (items: Item[]) => {
        this.items = items;
      }
    );
  }

  ngOnDestroy() {
    this.cartSubscription.unsubscribe();
  }

  onRemove(index: number) {
    this.cartService.removeFromCart(index);
  }
}

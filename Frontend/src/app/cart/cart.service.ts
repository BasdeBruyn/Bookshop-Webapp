import {Item} from '../item/item.model';
import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {Order} from './order.model';
import {environment} from '../../environments/environment';
import {AuthService} from '../auth/auth.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {CartItem} from './cart-item.model';

@Injectable()
export class CartService {

  private _items: Item[] = [
    // new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    // new Item('Fundamentals of database systems', 8000,
    //   '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true),
    // new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    // new Item('Fundamentals of database systems', 8000,
    //   '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true),
    // new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    // new Item('Fundamentals of database systems', 8000,
    //   '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true),
    // new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    // new Item('Fundamentals of database systems', 8000,
    //   '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true)
  ];
  private _cartItems: CartItem[] = [];
  private _itemObservable = new Subject<Item[]>();

  private _previousOrderObservable = new Subject<Order>();


  constructor(private authService: AuthService,
              private httpClient: HttpClient,
              private router: Router) {}

  getItems() {
    const userId = this.authService.user.id;
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.get<Item[]>(environment.server + 'cart/items/' + userId, httpOptions)
      .subscribe(
        response => {
          if (response !== null) {
            this._items = response;
            this.itemsUpdated();
          }
        },
        error => console.log(error)
      );
    this.httpClient.get<CartItem[]>(environment.server + 'cart/from/' + userId, httpOptions)
      .subscribe(
        response => {
          this._cartItems = response;
        },
        error => console.log(error)
      );
  }

  addToCart(itemId: number) {
    const cartItem = new CartItem(this.authService.user.id, itemId);
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.post(environment.server + 'cart', cartItem, httpOptions)
      .subscribe(
        response => this.getItems(),
        error => console.log(error)
      );
  }

  removeFromCart(index: number) {
    const cartItemId = this._cartItems[index].id;
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.delete(environment.server + 'cart/' + cartItemId, httpOptions)
      .subscribe(
        response => this.getItems(),
        error => console.log(error)
      );
  }

  itemsUpdated() {
    this._itemObservable.next(this._items);
  }

  getPreviousOrder() {
    const userId = this.authService.user.id;
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.get<Order>(environment.server + 'order/latest/' + userId, httpOptions)
      .subscribe(
        response => {
          this._previousOrderObservable.next(response);
        },
        error => console.log(error)
      );
  }

  emptyCart() {
    const userId = this.authService.user.id;
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.delete(environment.server + 'cart/from/' + userId, httpOptions)
      .subscribe(
        response => this.getItems(),
        error => console.log(error)
      );
  }

  order(order: Order) {
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.post(environment.server + 'order', order, httpOptions)
      .subscribe(
        response => {
          this.emptyCart();
          this.router.navigate(['']);
        },
        error => console.log(error)
      );
  }

  get items(): Item[] { return this._items; }
  get itemObservable(): Subject<Item[]> { return this._itemObservable; }
  get previousOrderObservable(): Subject<Order> { return this._previousOrderObservable; }
}

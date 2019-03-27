import {Item} from '../item/item.model';
import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {Order} from './order.model';

@Injectable()
export class CartService {

  private _items: Item[] = [
    new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    new Item('Fundamentals of database systems', 8000,
      '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true),
    new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    new Item('Fundamentals of database systems', 8000,
      '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true),
    new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    new Item('Fundamentals of database systems', 8000,
      '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true),
    new Item('Algorithms', 7000, '', 'https://algs4.cs.princeton.edu/cover.png', true),
    new Item('Fundamentals of database systems', 8000,
      '', 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', true)
  ];
  private _itemObservable = new Subject<Item[]>();

  private _previousOrder = new Order('Bas', 'de Bruyn', '2408TX', '95',
    'Amerikalaan', 'Alphen aan den Rijn', '+31642527672', 2000, -1);

  addToCart(item: Item) {
    this._items.push(item);
    this.itemsUpdated();
  }

  removeFromCart(index: number) {
    this._items.splice(index, 1);
    this.itemsUpdated();
  }

  itemsUpdated() {
    console.log(this._items);
    this._itemObservable.next(this._items);
  }

  order(order: Order) {
    console.log(order);
  }

  get items(): Item[] { return this._items; }
  get itemObservable(): Subject<Item[]> { return this._itemObservable; }
  get previousOrder(): Order { return this._previousOrder; }
}

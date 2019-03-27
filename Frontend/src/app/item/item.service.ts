import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Item} from './item.model';

@Injectable()
export class ItemService {
  lorumIpsum = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ' +
    'Donec sodales laoreet enim sed commodo. Proin vitae ero' +
    's felis. Praesent et condimentum dolor. Maecenas metus purus, c' +
    'ongue quis diam ac, bibendum suscipit justo. Nullam sed ullamcor' +
    'per nulla. Aenean egestas imperdiet nibh, vel imperdiet ipsum fac' +
    'ilisis id. Etiam elit enim, feugiat eu velit in, fringilla mollis' +
    ' justo. Morbi aliquet volutpat massa et ultricies. Duis lacinia ma' +
    'lesuada congue. Morbi non aliquet enim, auctor tristique sapien. V' +
    'estibulum ante ipsum primis in faucibus orci luctus et ultrices posu' +
    'ere cubilia Curae; Integer mollis quam ac nisi condimentum, sed vehicula risus lobortis.';

  private _items: Item[] = [
    new Item('Algorithms', 7000, this.lorumIpsum, 'https://algs4.cs.princeton.edu/cover.png', true),
    new Item('Fundamentals of database systems', 8000,
      this.lorumIpsum, 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', false)
  ];

  private _itemsObservable = new Subject<Item[]>();

  addItem(item: Item) {
    this._items.push(item);
    this.itemsUpdated();
  }

  updateItem(item: Item, index: number) {
    this._items[index] = item;
    this.itemsUpdated();
  }

  removeItem(index: number) {
    this._items.splice(index, 1);
    this.itemsUpdated();
  }

  itemsUpdated() {
    this._itemsObservable.next(this._items);
  }

  get itemsObservable(): Subject<Item[]> { return this._itemsObservable; }
  get items(): Item[] { return this._items; }
}

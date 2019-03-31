import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Item} from './item.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';

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

  // private _items: Item[] = [
  //   new Item('Algorithms', 7000, this.lorumIpsum, 'https://algs4.cs.princeton.edu/cover.png', true),
  //   new Item('Fundamentals of database systems', 8000,
  //     this.lorumIpsum, 'https://mediaserver.123library.org/9781292097626/cover/9781292097626.png', false)
  // ];

  private _items: Item[] = [];

  private _itemsObservable = new Subject<Item[]>();


  constructor(private httpClient: HttpClient,
              private authService: AuthService,
              private router: Router) {
    this.getItems();
  }

  addItem(item: Item) {
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.post(environment.server + 'item', item, httpOptions)
      .subscribe(
        response => setTimeout(
          () => this.router.navigate(['']),
          500
        ),
        error => console.log(error)
      );
  }

  updateItem(item: Item) {
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.put(environment.server + 'item', item, httpOptions)
      .subscribe(
        response => setTimeout(
          () => this.router.navigate(['']),
          500
        ),
        error => console.log(error)
      );
  }

  removeItem(id: number) {
    const httpOptions = {headers: this.authService.getAuthHeaders()};
    this.httpClient.delete(environment.server + 'item/' + id, httpOptions)
      .subscribe(
        response => setTimeout(
          () => this.router.navigate(['']),
          500
        ),
        error => console.log(error)
      );
  }

  getItems() {
    this.httpClient.get<Item[]>(environment.server + 'item')
      .subscribe(
        response => {
          this._items = response;
          this.itemsUpdated();
        },
        error => console.log(error)
      );
  }

  itemsUpdated() {
    this._itemsObservable.next(this._items);
  }

  get itemsObservable(): Subject<Item[]> { return this._itemsObservable; }
  get items(): Item[] { return this._items; }
}

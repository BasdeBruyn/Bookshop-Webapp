import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../auth/auth.service';
import {Item} from '../item.model';
import {ItemService} from '../item.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../auth/user.model';

@Component({
  selector: 'app-item-display',
  templateUrl: './item-list-display.component.html',
  styleUrls: ['./item-list-display.component.scss']
})
export class ItemListDisplayComponent implements OnInit, OnDestroy {
  private _items: Item[];
  private _itemsSubscription: Subscription;
  private _isAuthorized: boolean;
  private _authSubscription: Subscription;
  private _isAdmin: boolean;
  private _adminSubscription: Subscription;

  constructor(private authService: AuthService,
              private itemService: ItemService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.itemService.getItems();
    this._items = this.itemService.items;
    this._itemsSubscription = this.itemService.itemsObservable.subscribe(
      (updatedItems: Item[]) => {
        this._items = updatedItems;
    });
    this._isAuthorized = this.authService.isAuthorized;
    this._authSubscription = this.authService.authorizedObservable.subscribe(
      (isAuthorized: boolean) => {
        this._isAuthorized = isAuthorized;
      }
    );
    this._isAdmin = this.authService.isAdmin;
    this._adminSubscription = this.authService.adminObservable.subscribe(
      (isAdmin: boolean) => {
        this._isAdmin = isAdmin;
      }
    );
  }

  ngOnDestroy(): void {
    this._itemsSubscription.unsubscribe();
    this._authSubscription.unsubscribe();
    this._adminSubscription.unsubscribe();
  }

  onAddNewItem() {
    this.router.navigate(['new'], {relativeTo: this.route});
  }

  onItemClicked(id: number) {
    if (this.authService.isAdmin) {
      this.router.navigate([id, 'edit'], {relativeTo: this.route});
    } else {
      this.router.navigate([id], {relativeTo: this.route});
    }
  }
}

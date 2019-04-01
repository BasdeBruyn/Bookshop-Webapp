import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../auth/auth.service';
import {Item} from '../item.model';
import {ItemService} from '../item.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-item-display',
  templateUrl: './item-list-display.component.html',
  styleUrls: ['./item-list-display.component.scss']
})
export class ItemListDisplayComponent implements OnInit, OnDestroy {
  public items: Item[];
  public itemsSubscription: Subscription;
  public isAuthorized: boolean;
  public authSubscription: Subscription;
  public isAdmin: boolean;
  public adminSubscription: Subscription;

  constructor(public authService: AuthService,
              public itemService: ItemService,
              public router: Router,
              public route: ActivatedRoute) { }

  ngOnInit() {
    this.itemService.getItems();
    this.items = this.itemService.items;
    this.itemsSubscription = this.itemService.itemsObservable.subscribe(
      (updatedItems: Item[]) => {
        this.items = updatedItems;
    });
    this.isAuthorized = this.authService.isAuthorized;
    this.authSubscription = this.authService.authorizedObservable.subscribe(
      (isAuthorized: boolean) => {
        this.isAuthorized = isAuthorized;
      }
    );
    this.isAdmin = this.authService.isAdmin;
    this.adminSubscription = this.authService.adminObservable.subscribe(
      (isAdmin: boolean) => {
        this.isAdmin = isAdmin;
      }
    );
  }

  ngOnDestroy(): void {
    this.itemsSubscription.unsubscribe();
    this.authSubscription.unsubscribe();
    this.adminSubscription.unsubscribe();
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

import {Component, ElementRef, OnInit} from '@angular/core';
import {AuthService} from '../../auth/auth.service';
import {Item} from '../../shared/item.model';
import {ItemService} from '../../shared/item.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-item-display',
  templateUrl: './item-list-display.component.html',
  styleUrls: ['./item-list-display.component.scss']
})
export class ItemListDisplayComponent implements OnInit {
  items: Item[];
  private itemsSubscription: Subscription;

  constructor(private authService: AuthService,
              private itemService: ItemService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.items = this.itemService.items;
    this.itemsSubscription = this.itemService.itemsObservable.subscribe(
      (updatedItems: Item[]) => {
        this.items = updatedItems;
    });
  }

  onAddNewItem() {
    this.router.navigate(['new'], {relativeTo: this.route});
  }
}

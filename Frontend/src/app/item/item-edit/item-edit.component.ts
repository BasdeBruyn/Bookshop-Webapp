import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {ItemService} from '../item.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {Item} from '../item.model';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.scss']
})
export class ItemEditComponent implements OnInit, OnDestroy {
  private _item: Item;
  private _editMode = false;
  private _routeSubscription: Subscription;

  @ViewChild('form') form: NgForm;

  constructor(private itemService: ItemService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this._routeSubscription = this.route.params.subscribe(
      (params: Params) => {
        if (params['id']) {
          this._item = this.itemService.items[+params['id']];
          setTimeout(() => {
            this.loadItem();
          });
          this._editMode = true;
        }
      }
    );
  }

  ngOnDestroy(): void {
    this._routeSubscription.unsubscribe();
  }

  onSubmit() {
    const item: Item = this.form.value;
    if (this._editMode) {
      item.id = this._item.id;
      this.itemService.updateItem(item);
    } else {
      this.itemService.addItem(this.form.value);
    }
  }

  loadItem() {
    this.form.setValue({
      name: this._item.name,
      price: this._item.price,
      description: this._item.description,
      url: this._item.url,
      available: this._item.available
    });
  }

  onCancel() {
      this.router.navigate(['/']);
  }

  onDelete() {
    this.itemService.removeItem(this._item.id);
  }

  onReset() {
    this.form.resetForm();
  }
}

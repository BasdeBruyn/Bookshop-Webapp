import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {ItemService} from '../item.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.scss']
})
export class ItemEditComponent implements OnInit, OnDestroy {
  private _itemId: number;
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
          this._itemId = +params['id'];
          setTimeout(() => {
            this.loadItem(this._itemId);
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
    if (this._editMode) {
      this.itemService.updateItem(this.form.value, this._itemId);
    } else {
      this.itemService.addItem(this.form.value);
    }
    this.router.navigate(['']);
  }

  loadItem(id: number) {
    const item = this.itemService.items[id];
    this.form.setValue({
      name: item.name,
      price: item.price,
      description: item.description,
      url: item.url,
      available: item.available
    });
  }

  onCancel() {
    this.form.resetForm();
  }

  onDelete() {
    this.itemService.removeItem(this._itemId);
    this.router.navigate(['']);
  }
}

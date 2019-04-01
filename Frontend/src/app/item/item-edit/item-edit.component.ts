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
  public item: Item;
  public editMode = false;
  public routeSubscription: Subscription;

  @ViewChild('form') form: NgForm;

  constructor(public itemService: ItemService,
              public route: ActivatedRoute,
              public router: Router) { }

  ngOnInit() {
    this.routeSubscription = this.route.params.subscribe(
      (params: Params) => {
        if (params['id']) {
          this.item = this.itemService.items[+params['id']];
          setTimeout(() => {
            this.loadItem();
          });
          this.editMode = true;
        }
      }
    );
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
  }

  onSubmit() {
    const item: Item = this.form.value;
    if (this.editMode) {
      item.id = this.item.id;
      this.itemService.updateItem(item);
    } else {
      this.itemService.addItem(this.form.value);
    }
  }

  loadItem() {
    this.form.setValue({
      name: this.item.name,
      price: this.item.price,
      description: this.item.description,
      url: this.item.url,
      available: this.item.available
    });
  }

  onCancel() {
      this.router.navigate(['/']);
  }

  onDelete() {
    this.itemService.removeItem(this.item.id);
  }

  onReset() {
    this.form.resetForm();
  }
}

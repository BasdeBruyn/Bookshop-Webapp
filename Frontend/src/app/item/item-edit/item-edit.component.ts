import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {ItemService} from '../../shared/item.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Item} from '../../shared/item.model';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.scss']
})
export class ItemEditComponent implements OnInit {
  itemId: number;
  editMode = false;

  @ViewChild('form') form: NgForm;

  constructor(private itemService: ItemService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(
      (params: Params) => {
        if (params['id']) {
          this.itemId = +params['id'];
          setTimeout(() => {
            this.loadItem(this.itemId);
          });
          this.editMode = true;
        }
      }
    );
  }

  onSubmit() {
    if (this.editMode) {
      this.itemService.updateItem(this.form.value, this.itemId);
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
    this.itemService.removeItem(this.itemId);
    this.router.navigate(['']);
  }
}

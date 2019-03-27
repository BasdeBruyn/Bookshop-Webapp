import {Component, OnInit, ViewChild} from '@angular/core';
import {CartService} from '../cart.service';
import {Item} from '../../item/item.model';
import {Order} from '../order.model';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-cart-checkout',
  templateUrl: './cart-checkout.component.html',
  styleUrls: ['./cart-checkout.component.scss']
})
export class CartCheckoutComponent implements OnInit {

  private _items: Item[];
  private _totalPrice = 0;
  private _previousOrder: Order;
  @ViewChild('form') private _form: NgForm;

  constructor(private cartService: CartService) { }

  ngOnInit() {
    this._items = this.cartService.items;
    for (const item of this._items) {
      this._totalPrice += item.price;
    }
    this._previousOrder = this.cartService.previousOrder;
    setTimeout( () => {
      this._form.setValue({
        firstName: this._previousOrder.firstName,
        lastName: this._previousOrder.lastName,
        postalCode: this._previousOrder.postalCode,
        houseNr: this._previousOrder.houseNr,
        streetName: this._previousOrder.streetName,
        residence: this._previousOrder.residence,
        phoneNr: this._previousOrder.phoneNr,
      });
    });
  }

  onSubmit() {
    const order: Order = this._form.value;
    order.totalPrice = this._totalPrice;
    this.cartService.order(order);
  }
}

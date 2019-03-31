import {Component, OnInit, ViewChild} from '@angular/core';
import {CartService} from '../cart.service';
import {Item} from '../../item/item.model';
import {Order} from '../order.model';
import {NgForm} from '@angular/forms';
import {Subscription} from 'rxjs';
import {AuthService} from '../../auth/auth.service';

@Component({
  selector: 'app-cart-checkout',
  templateUrl: './cart-checkout.component.html',
  styleUrls: ['./cart-checkout.component.scss']
})
export class CartCheckoutComponent implements OnInit {

  private _items: Item[];
  private _totalPrice = 0;
  private _previousOrderSubscription: Subscription;
  @ViewChild('form') private _form: NgForm;

  constructor(private cartService: CartService,
              private authService: AuthService) { }

  ngOnInit() {
    this._items = this.cartService.items;
    for (const item of this._items) {
      this._totalPrice += item.price;
    }
    this.cartService.getPreviousOrder();
    this._previousOrderSubscription = this.cartService.previousOrderObservable.subscribe(
      (order: Order) => {
        if (order !== null) {
          this.loadPreviousOrder(order);
        }
      }
    );
  }

  onSubmit() {
    const order: Order = this._form.value;
    order.totalPrice = this._totalPrice;
    order.userId = this.authService.user.id;
    this.cartService.order(order);
  }

  loadPreviousOrder(order: Order) {
    this._form.setValue({
      firstName: order.firstName,
      lastName: order.lastName,
      postalCode: order.postalCode,
      houseNr: order.houseNr,
      streetName: order.streetName,
      residence: order.residence,
      phoneNr: order.phoneNr,
    });
  }
}

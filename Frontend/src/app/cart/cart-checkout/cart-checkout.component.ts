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

  public items: Item[];
  public totalPrice = 0;
  public previousOrderSubscription: Subscription;
  @ViewChild('form') public _form: NgForm;

  constructor(public cartService: CartService,
              public authService: AuthService) { }

  ngOnInit() {
    this.items = this.cartService.items;
    for (const item of this.items) {
      this.totalPrice += item.price;
    }
    this.cartService.getPreviousOrder();
    this.previousOrderSubscription = this.cartService.previousOrderObservable.subscribe(
      (order: Order) => {
        if (order !== null) {
          this.loadPreviousOrder(order);
        }
      }
    );
  }

  onSubmit() {
    const order: Order = this._form.value;
    order.totalPrice = this.totalPrice;
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

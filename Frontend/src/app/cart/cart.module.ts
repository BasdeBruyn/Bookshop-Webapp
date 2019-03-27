import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CartRoutingModule} from './cart-routing.module';
import {CartComponent} from './cart.component';
import {CartListComponent} from './cart-list/cart-list.component';
import {CartCheckoutComponent} from './cart-checkout/cart-checkout.component';
import {AuthGuard} from '../auth/auth-guard.service';
import {AuthService} from '../auth/auth.service';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    CartComponent,
    CartListComponent,
    CartCheckoutComponent
  ],
  imports: [
    CommonModule,
    CartRoutingModule,
    FormsModule
  ],
  providers: [
    AuthGuard,
    AuthService
  ]
})
export class CartModule { }

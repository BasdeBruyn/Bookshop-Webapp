import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CartComponent} from './cart.component';
import {CartListComponent} from './cart-list/cart-list.component';
import {CartCheckoutComponent} from './cart-checkout/cart-checkout.component';
import {AuthGuard} from '../auth/auth-guard.service';

const routes: Routes = [
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard], children: [
      { path: '', component: CartListComponent },
      { path: 'checkout', component: CartCheckoutComponent }
    ]}
];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class CartRoutingModule { }

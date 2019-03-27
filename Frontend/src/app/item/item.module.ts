import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ItemListDisplayComponent} from './item-list-display/item-list-display.component';
import {ItemRoutingModule} from './item-routing.module';
import {ShortenPipe} from '../shared/shorten.pipe';
import { ItemEditComponent } from './item-edit/item-edit.component';
import {ItemComponent} from './item.component';
import {FormsModule} from '@angular/forms';
import { ItemDisplayComponent } from './item-display/item-display.component';
import {AuthGuard} from '../auth/auth-guard.service';
import {AuthService} from '../auth/auth.service';
import {AdminGuard} from '../auth/admin-guard.service';
import {CartService} from '../cart/cart.service';

@NgModule({
  declarations: [
    ItemListDisplayComponent,
    ShortenPipe,
    ItemEditComponent,
    ItemComponent,
    ItemDisplayComponent
  ],
  imports: [
    CommonModule,
    ItemRoutingModule,
    FormsModule
  ],
  providers: [
    AuthGuard,
    AuthService,
    AdminGuard,
    CartService
  ]
})
export class ItemModule { }

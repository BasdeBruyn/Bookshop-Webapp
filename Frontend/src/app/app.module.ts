import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import {AuthService} from './auth/auth.service';
import {ItemService} from './item/item.service';
import {ItemModule} from './item/item.module';
import {AuthModule} from './auth/auth.module';
import {CartModule} from './cart/cart.module';
import {MDBBootstrapModule} from 'angular-bootstrap-md';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ItemModule,
    AuthModule,
    CartModule,
    MDBBootstrapModule.forRoot()
  ],
  providers: [
    AuthService,
    ItemService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

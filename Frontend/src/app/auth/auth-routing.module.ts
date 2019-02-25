import {RouterModule, Routes} from '@angular/router';
import {ItemListDisplayComponent} from '../item/item-list-display/item-list-display.component';
import {NgModule} from '@angular/core';

const routes: Routes = [
  { path: '/auth', component: ItemListDisplayComponent, children: [
      { path: 'new', component: ItemListDisplayComponent},
      { path: ':id', component: ItemListDisplayComponent},
      { path: ':id/edit', component: ItemListDisplayComponent},
    ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {}

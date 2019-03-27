import {RouterModule, Routes} from '@angular/router';
import {ItemListDisplayComponent} from './item-list-display/item-list-display.component';
import {NgModule} from '@angular/core';
import {ItemEditComponent} from './item-edit/item-edit.component';
import {ItemComponent} from './item.component';
import {ItemDisplayComponent} from './item-display/item-display.component';
import {AdminGuard} from '../auth/admin-guard.service';
import {AuthGuard} from '../auth/auth-guard.service';

const routes: Routes = [
  { path: 'items', component: ItemComponent, children: [
      { path: '', component: ItemListDisplayComponent },
      { path: 'new', component: ItemEditComponent , canActivate: [AdminGuard, AuthGuard] },
      { path: ':id', component: ItemDisplayComponent },
      { path: ':id/edit', component: ItemEditComponent, canActivate: [AdminGuard, AuthGuard] }
    ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ItemRoutingModule {}

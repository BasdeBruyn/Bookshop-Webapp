import {RouterModule, Routes} from '@angular/router';
import {ItemListDisplayComponent} from './item-list-display/item-list-display.component';
import {NgModule} from '@angular/core';
import {ItemEditComponent} from './item-edit/item-edit.component';
import {ItemComponent} from './item.component';

const routes: Routes = [
  { path: 'items', component: ItemComponent, children: [
      { path: '', component: ItemListDisplayComponent },
      { path: 'new', component: ItemEditComponent },
      { path: ':id', component: ItemEditComponent },
      { path: ':id/edit', component: ItemEditComponent },
    ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ItemRoutingModule {}

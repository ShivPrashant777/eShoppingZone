import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddProductComponent } from './components/add-product/add-product.component';
import { LoginPageComponent } from './components/auth/login-page/login-page.component';
import { RegisterPageComponent } from './components/auth/register-page/register-page.component';
import { CartComponent } from './components/cart/cart.component';
import { EditProductComponent } from './components/edit-product/edit-product.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { OrdersComponent } from './components/orders/orders.component';
import { ProductPageComponent } from './components/product-page/product-page.component';
import { ProductsCatalogueComponent } from './components/products-catalogue/products-catalogue.component';
import { AuthGuard } from './routeguards/auth.guard';
import { CustomerAuthGuard } from './routeguards/customer-auth.guard';
import { MerchantAuthGuard } from './routeguards/merchant-auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/products', pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  {
    path: 'profile/edit-profile',
    component: EditProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'profile/orders',
    component: OrdersComponent,
    canActivate: [CustomerAuthGuard],
  },
  { path: 'cart', component: CartComponent, canActivate: [CustomerAuthGuard] },
  {
    path: 'products',
    component: ProductsCatalogueComponent,
  },
  {
    path: 'products/add-product',
    component: AddProductComponent,
    canActivate: [MerchantAuthGuard],
  },
  { path: 'products/:productId', component: ProductPageComponent },
  { path: 'products/edit-product/:productId', component: EditProductComponent },
  { path: '**', redirectTo: '/products', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

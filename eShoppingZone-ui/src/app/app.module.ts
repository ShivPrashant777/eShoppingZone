import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductsCatalogueComponent } from './components/products-catalogue/products-catalogue.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CartComponent } from './components/cart/cart.component';
import { CartBarComponent } from './components/cart-bar/cart-bar.component';
import { CartItemComponent } from './components/cart-item/cart-item.component';
import { CounterBtnComponent } from './components/counter-btn/counter-btn.component';
import { ProductPageComponent } from './components/product-page/product-page.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ObjectLengthPipe } from './util/object-length.pipe';
import { AlertBoxComponent } from './components/alert-box/alert-box.component';
import { LoginPageComponent } from './components/auth/login-page/login-page.component';
import { RegisterPageComponent } from './components/auth/register-page/register-page.component';
import { XhrInterceptor } from './interceptors/app.request.interceptor';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { EditProductComponent } from './components/edit-product/edit-product.component';
import { OrdersComponent } from './components/orders/orders.component';
import { OrderItemComponent } from './components/order-item/order-item.component';
import { initializeApp } from 'firebase/app';
import { firebaseConfig } from './firebase.config';
import { AngularFireModule } from '@angular/fire/compat'; // Updated import
import { AngularFireStorageModule } from '@angular/fire/compat/storage';

@NgModule({
  declarations: [
    AppComponent,
    ProductsCatalogueComponent,
    NavbarComponent,
    ProductCardComponent,
    CartComponent,
    CartBarComponent,
    CartItemComponent,
    CounterBtnComponent,
    ProductPageComponent,
    CounterBtnComponent,
    AddProductComponent,
    ObjectLengthPipe,
    AlertBoxComponent,
    LoginPageComponent,
    RegisterPageComponent,
    EditProfileComponent,
    EditProductComponent,
    OrdersComponent,
    OrderItemComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AngularFireModule.initializeApp(firebaseConfig),
    AngularFireStorageModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: XhrInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

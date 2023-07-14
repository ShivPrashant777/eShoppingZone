import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Cart } from 'src/app/models/Cart.model';
import { Product } from 'src/app/models/Product.model';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-counter-btn',
  templateUrl: './counter-btn.component.html',
  styleUrls: ['./counter-btn.component.css'],
})
export class CounterBtnComponent implements OnInit, OnDestroy {
  @Input() public product: Product = new Product(
    '',
    '',
    '',
    {},
    {},
    '',
    0,
    '',
    {},
    0
  );

  public cart: Cart = new Cart(0, [], 0);
  public user: User | null = null;
  public isAuthenticated = false;
  private authSubscription: Subscription = new Subscription();
  public cartSubscription: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    private cartService: CartService
  ) {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
      this.isAuthenticated = user ? true : false;
    });
    this.cartSubscription = this.cartService.cartUpdated.subscribe((cart) => {
      this.cart = cart;
    });
  }

  ngOnInit() {
    this.product.quantity = 0;
    if (this.user) {
      this.cartService.getCart(this.user.profileId).subscribe((response) => {
        this.cart = <Cart>response.body;

        for (let p of this.cart.products) {
          if (p.productId === this.product.productId) {
            this.product.quantity = p.quantity;
          }
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
    this.cartSubscription.unsubscribe();
  }

  public addToCart() {
    this.product.quantity = 1;
    this.cart.products.push(this.product);
    this.updateCart();
  }

  public decreaseQuantity() {
    if (this.product.quantity > 0) {
      this.product.quantity--;
      const i = this.cart.products.findIndex(
        (p) => p.productId === this.product.productId
      );
      this.cart.products[i].quantity = this.product.quantity;

      if (this.product.quantity === 0) {
        this.cart.products.splice(i, 1);
      }
    }

    this.updateCart();
  }

  public increaseQuantity() {
    this.product.quantity++;
    const i = this.cart.products.findIndex(
      (p) => p.productId === this.product.productId
    );
    this.cart.products[i].quantity = this.product.quantity;
    this.updateCart();
  }

  public updateCart() {
    // calculate total Price
    this.cart.totalPrice = 0;
    for (let i of this.cart.products) {
      this.cart.totalPrice += i.price * i.quantity;
    }
    this.cartService.updateCart(this.cart);
    this.cartService.cartUpdated.emit(this.cart);
  }
}

import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  OnDestroy,
} from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Cart } from 'src/app/models/Cart.model';
import { Product } from 'src/app/models/Product.model';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
})
export class ProductCardComponent implements OnInit, OnDestroy {
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
  public avgRating = 0;
  @Output() public addToCartEvent = new EventEmitter();
  public cart: Cart = new Cart(0, [], 0);
  public user: User | null = null;
  private authSubscription: Subscription = new Subscription();
  public cartSubscription: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private productService: ProductService,
    private router: Router
  ) {
    this.cartSubscription = this.cartService.cartUpdated.subscribe((cart) => {
      this.cart = cart;
    });
  }

  ngOnInit() {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
    });
    this.avgRating = this.productService.getAverageRating(this.product);
  }

  public redirectToProductPage() {
    this.router.navigate(['/products', this.product.productId]);
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
    this.cartSubscription.unsubscribe();
  }
}

import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Cart } from 'src/app/models/Cart.model';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { Product } from '../../models/Product.model';

@Component({
  selector: 'app-products-catalogue',
  templateUrl: './products-catalogue.component.html',
  styleUrls: ['./products-catalogue.component.css'],
})
export class ProductsCatalogueComponent implements OnInit {
  public isLoading: boolean = true;
  public products: Product[] = [];
  public cart: Cart = new Cart(0, [], 0);
  public user: User | null = null;
  public isAuthenticated = false;
  private authSubscription: Subscription = new Subscription();
  public cartUpdatedSubscription: Subscription = new Subscription();
  public cartGetCartSubscription: Subscription = new Subscription();
  public productGetAllSubscription: Subscription = new Subscription();
  public productUpdatedSubscription: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    public productService: ProductService,
    public cartService: CartService
  ) {
    this.productUpdatedSubscription =
      this.productService.productsUpdated.subscribe((products) => {
        this.products = products;
      });
    this.cartUpdatedSubscription = this.cartService.cartUpdated.subscribe(
      (cart) => {
        this.cart = cart;
      }
    );
  }

  ngOnInit() {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
      this.isAuthenticated = user ? true : false;
      if (this.user) {
        this.cartGetCartSubscription = this.cartService
          .getCart(this.user.profileId)
          .subscribe((response) => {
            this.cart = <Cart>response.body;
          });
      }
    });

    this.productGetAllSubscription = this.productService
      .getAllProducts()
      .subscribe((response) => {
        this.products = <Product[]>response.body;
        this.isLoading = false;
      });
  }

  onSortChange(event: any) {
    if (event.target.value === 'Name') {
      this.productService.sortByName().subscribe((response) => {
        this.productService.productsUpdated.emit(<Product[]>response.body);
      });
    } else {
      this.productService.sortByPrice().subscribe((response) => {
        this.productService.productsUpdated.emit(<Product[]>response.body);
      });
    }
  }

  onSortByPrice() {}

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
    this.productUpdatedSubscription.unsubscribe();
    this.productGetAllSubscription.unsubscribe();
    this.cartUpdatedSubscription.unsubscribe();
    this.cartGetCartSubscription.unsubscribe();
  }
}

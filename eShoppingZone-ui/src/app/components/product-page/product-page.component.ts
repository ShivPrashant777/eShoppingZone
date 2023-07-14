import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/Product.model';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css'],
})
export class ProductPageComponent implements OnInit, OnDestroy {
  public isLoading: boolean = true;
  public product: any;
  public avgRating = 0;
  public displaySpec: boolean = true;
  public user: User | null = null;
  private authSubscription: Subscription = new Subscription();
  public productSubscription: Subscription = new Subscription();

  constructor(
    public route: ActivatedRoute,
    private authService: AuthService,
    public productService: ProductService
  ) {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      let productId = params.get('productId');
      this.productSubscription = this.productService
        .getProductById(productId)
        .subscribe((response) => {
          this.product = <Product>response.body;
          this.isLoading = false;
          this.avgRating = this.productService.getAverageRating(this.product);
          console.log(this.product);
        });
    });
  }

  public showSpecs() {
    this.displaySpec = true;
  }

  public showReviews() {
    this.displaySpec = false;
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
    this.productSubscription.unsubscribe();
  }
}

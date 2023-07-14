import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/Product.model';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnDestroy {
  private authSubscription: Subscription = new Subscription();
  private productSubscription: Subscription = new Subscription();
  public user: User | null = null;
  public displayNav = true;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router
  ) {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
    });
    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        console.log(this.router.url);
        this.displayNav = this.router.url !== '/login';
      }
    });
  }

  onSearchChange(searchString: string): void {
    this.productSubscription = this.productService
      .searchProducts(searchString)
      .subscribe((response) => {
        this.productService.productsUpdated.emit(<Product[]>response.body);
      });
  }

  ngOnDestroy(): void {
    this.productSubscription.unsubscribe();
    this.authSubscription.unsubscribe();
  }

  onLogout() {
    this.authService.logout();
  }
}

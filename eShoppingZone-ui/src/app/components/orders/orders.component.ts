import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Order } from 'src/app/models/Order.model';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit, OnDestroy {
  public isLoading: boolean = true;
  public orders: Order[] = [];
  public user: User | null = null;
  private authSubscription: Subscription = new Subscription();
  private orderSubscription: Subscription = new Subscription();
  constructor(
    private authService: AuthService,
    private orderService: OrderService
  ) {}

  ngOnInit() {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
      if (this.user) {
        this.orderSubscription = this.orderService
          .getAllOrdersByCustomerId(this.user.profileId)
          .subscribe((res) => {
            this.orders = <Order[]>res;
            console.log('Orders', this.orders);
            this.isLoading = false;
          });
      }
    });
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
    this.orderSubscription.unsubscribe();
  }
}

import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Address } from 'src/app/models/Address.model';
import { Alert } from 'src/app/models/Alert.model';
import { Cart } from 'src/app/models/Cart.model';
import { Item } from 'src/app/models/Item.model';
import { OrderRequest } from 'src/app/models/OrderRequest.model';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

declare var Razorpay: any;
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit, OnDestroy {
  public cart: Cart = new Cart(0, [], 0);
  public user: User | null = null;
  public isAuthenticated = false;
  private authSubscription: Subscription = new Subscription();
  public cartSubscription: Subscription = new Subscription();
  public addressSubscription: Subscription = new Subscription();

  public addresses: Address[] = [];
  public deliveryAddress: string = '';
  public alert: Alert = new Alert('', '');

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private orderService: OrderService
  ) {}

  ngOnInit() {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
      this.isAuthenticated = user ? true : false;
      if (this.user) {
        this.cartSubscription = this.cartService
          .getCart(this.user.profileId)
          .subscribe((response) => {
            this.cart = <Cart>response.body;
          });

        this.addressSubscription = this.orderService
          .getUserAddressesByProfileId(this.user.profileId)
          .subscribe((res) => {
            this.addresses = <Address[]>res;
            console.log('Addresses', this.addresses);
          });
      }
    });
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
    this.cartSubscription.unsubscribe();
    this.addressSubscription.unsubscribe();
  }

  createTransactionAndPlaceOrder() {
    const amount = this.cart.totalPrice ? this.cart.totalPrice : 0;
    if (amount !== 0) {
      this.orderService.createTransaction(amount).subscribe(
        (res) => {
          console.log(res);
          this.openTransactionModal(res);
        },
        (err) => {
          console.log(err);
        }
      );
    }
  }

  public openTransactionModal(response: any) {
    var options = {
      order_id: response.orderId,
      key: response.key,
      amount: response.amount,
      currency: response.currency,
      name: 'eShoppingZone',
      description: 'Payment of Online Shopping',
      handler: (res: any) => {
        if (res !== null && res.razorpay_payment_id !== null) {
          this.processResponse(res);
        } else {
          alert('Payment failed...');
        }
      },
      prefill: {
        name: 'Jack Ryan',
        email: 'jackryan@gmail.com',
        contact: '9989796959',
      },
      notes: {
        address: 'Jack Ryan Corp',
      },
      theme: {
        color: '#00457E',
      },
    };

    var razorpayObject = new Razorpay(options);
    razorpayObject.open();
  }

  public processResponse(res: any) {
    console.log(res);
    let items: Item[] = [];
    this.cart.products.map((p) => {
      let productId = p.productId ? p.productId : 0;
      items.push(new Item(productId, p.price, p.quantity));
    });

    const totalPrice = this.cart.totalPrice ? this.cart.totalPrice : 0;
    const orderRequest = new OrderRequest(
      res.razorpay_order_id,
      res.razorpay_payment_id,
      this.deliveryAddress,
      {
        cartId: this.cart.cartId,
        items: items,
        totalPrice: totalPrice,
      }
    );
    this.orderService.placeOrder(orderRequest).subscribe(
      (res) => {
        this.alert = new Alert('info', `Order Placed Successfully`);
        this.cart.products = [];
        this.cart.totalPrice = 0;
        this.cartService.cartUpdated.emit(this.cart);
      },
      (err) => {
        this.alert = new Alert('error', `Error: ${err.error.message}`);
      }
    );
  }
}

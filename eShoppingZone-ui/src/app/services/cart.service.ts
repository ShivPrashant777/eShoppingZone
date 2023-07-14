import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Cart } from '../models/Cart.model';
import { Item } from '../models/Item.model';
import { AppConstants } from '../constants/app.constants';

const { API_GATEWAY_URL, CART_SERVICE_URL } = AppConstants;

@Injectable({
  providedIn: 'root',
})
export class CartService {
  // public cart: Cart = new Cart(0, [], 0);
  public items: Item[] = [];
  constructor(private http: HttpClient) {}

  public cartUpdated = new EventEmitter<Cart>();

  public addCart(cartId: number) {
    return this.http.post(
      `${API_GATEWAY_URL}/${CART_SERVICE_URL}/addCart`,
      { cartId: cartId, items: [] },
      { observe: 'response' }
    );
  }

  public getCart(cartId: number) {
    return this.http.get(
      `${API_GATEWAY_URL}/${CART_SERVICE_URL}/getCart/${cartId}`,
      {
        observe: 'response',
      }
    );
  }

  public updateCart(cart: Cart) {
    this.items = [];
    cart.products.map((p) => {
      let productId = p.productId ? p.productId : 0;
      this.items.push(new Item(productId, p.price, p.quantity));
    });

    var body = {
      cartId: cart.cartId,
      items: this.items,
      totalPrice: cart.totalPrice,
    };

    this.http
      .put(`${API_GATEWAY_URL}/${CART_SERVICE_URL}/updateCart`, body, {
        observe: 'body',
      })
      .subscribe((res) => {
        console.log(res);
      });
  }
}

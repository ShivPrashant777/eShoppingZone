import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Product } from 'src/app/models/Product.model';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.css'],
})
export class CartItemComponent {
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
  @Output() public addToCartEvent = new EventEmitter();
  public isAdded: boolean = false;

  public decreaseQuantity() {
    if (this.product.quantity > 0) {
      this.product.quantity--;
    }

    if (this.product.quantity == 0) {
      this.isAdded = false;
    }
    this.addToCartEvent.emit(this.product);
  }

  public increaseQuantity() {
    this.product.quantity++;
    this.addToCartEvent.emit(this.product);
  }
}

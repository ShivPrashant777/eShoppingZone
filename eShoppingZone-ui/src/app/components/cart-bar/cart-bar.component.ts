import { Component, Input } from '@angular/core';
import { Cart } from 'src/app/models/Cart.model';

@Component({
  selector: 'app-cart-bar',
  templateUrl: './cart-bar.component.html',
  styleUrls: ['./cart-bar.component.css'],
})
export class CartBarComponent {
  @Input() public cart: Cart = new Cart(0, [], 0);
}

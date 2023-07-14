import { Component, Input } from '@angular/core';
import { Order } from 'src/app/models/Order.model';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.css'],
})
export class OrderItemComponent {
  @Input() public order: Order = new Order('', '', new Date(), 0, 0, []);
}

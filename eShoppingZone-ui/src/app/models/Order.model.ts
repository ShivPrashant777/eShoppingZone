import { Address } from './Address.model';
import { Product } from './Product.model';

export class Order {
  constructor(
    public orderId: String,
    public paymentId: String,
    public orderDate: Date,
    public customerId: number,
    public amountPaid: number,
    public products: Product[],
    public address?: Address
  ) {}
}

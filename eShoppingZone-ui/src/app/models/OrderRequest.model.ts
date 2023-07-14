import { Item } from './Item.model';

interface Cart {
  cartId: number;
  items: Item[];
  totalPrice: number;
}

export class OrderRequest {
  constructor(
    public orderId: string,
    public paymentId: string,
    public addressId: string,
    public cart: Cart
  ) {}
}

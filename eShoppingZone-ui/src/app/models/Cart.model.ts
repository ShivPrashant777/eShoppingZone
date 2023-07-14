import { Product } from './Product.model';

export class Cart {
  constructor(
    public cartId: number,
    public products: Product[],
    public totalPrice?: number
  ) {}
}

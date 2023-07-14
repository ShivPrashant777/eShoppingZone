export class Product {
  constructor(
    public productType: string,
    public productName: string,
    public category: string,
    public rating: object,
    public review: object,
    public image: string,
    public price: number,
    public description: string,
    public specification: { [key: string]: string },
    public quantity: number,
    public productId?: number
  ) {}
}

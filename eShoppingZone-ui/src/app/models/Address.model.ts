export class Address {
  constructor(
    public houseNumber: string,
    public streetName: string,
    public colonyName: string,
    public city: string,
    public state: string,
    public pincode: string,
    public addressId?: string
  ) {}
}

export class Order {
  constructor(
    public firstName: string,
    public lastName: string,
    public postalCode: string,
    public houseNr: string,
    public streetName: string,
    public residence: string,
    public phoneNr: string,
    public totalPrice: number,
    public userId: number,
    public id: number = -1
  ) {}
}

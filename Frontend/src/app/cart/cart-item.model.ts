export class CartItem {
  constructor(
    public userId: number,
    public itemId: number,
    public id: number = -1
  ) {}
}

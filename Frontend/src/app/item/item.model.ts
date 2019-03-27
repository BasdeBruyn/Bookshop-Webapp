export class Item {

  constructor(public name: string,
              public price: number,
              public description: string,
              public url: string,
              public available: boolean,
              public id: number = -1) {}
}

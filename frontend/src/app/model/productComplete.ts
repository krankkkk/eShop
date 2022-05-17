import { Info } from './info';
import { Price } from './price';
import { Image } from './image';

export class ProductComplete {
  id!: number;
  name!: string;
  productType!: string;
  price!: Price;
  info!: Info;
  image!: Image;

  constructor() {
    this.price = new Price();
    this.info = new Info();
  }
}

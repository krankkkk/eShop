export class Price {
  // das Price Objekt befindet sich in einem Product Objekt, deswegen braucht es eigentlich keine Product ID
  priceID!: number;
  price!: number;
  start!: Date;
  end!: Date;
  isDiscount!: boolean;
}

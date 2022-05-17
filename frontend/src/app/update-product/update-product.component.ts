import { PriceService } from './../services/price.service';
import { Price } from './../model/price';
import { ProductService } from './../services/product.service';
import { Component, OnInit } from '@angular/core';
import { ProductComplete } from '../model/productComplete';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {

  productComplete: ProductComplete = new ProductComplete();

  constructor(private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private priceService: PriceService) { }

  ngOnInit(): void {
    this.productComplete.id = this.route.snapshot.params['id'];

    this.productService.getProductById(this.productComplete.id).subscribe( data => {
      this.productComplete = data;
    }, error => console.log('Product konnte nicht geladen werden: ', error));

    this.priceService.getPriceByID(this.productComplete.id).subscribe( data => {
      this.productComplete.price = data;
    })
  }

  onSubmit() {
    // rein theoretisch könnte man hier noch prüfen ob überhaupt ein Feld geändert wurde
    this.productService.updateProduct(this.productComplete).subscribe( data => {
      console.log('Produkt wurde erfolgreich überschrieben', data);
      this.goToProductList();
    }, error => console.log('ERROR: ', error))
  }


  goToProductList() {
    this.router.navigate(['/products']);
  }


  onDiscountChanged(value: boolean) {
    this.productComplete.price.isDiscount = value;
  }
}

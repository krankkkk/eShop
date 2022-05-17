import { ImageService } from './../services/image.service';
import { InfoService } from './../services/info.service';
import { PriceService } from './../services/price.service';
import { Router } from '@angular/router';
import { ProductService } from './../services/product.service';
import { ProductComplete } from '../model/productComplete';
import { Component, Input, OnInit } from '@angular/core';
import { Price } from '../model/price';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  @Input() type = '';

  products!: ProductComplete[];

  // injecten des Service
  constructor(private productService: ProductService,
    private priceService: PriceService,
    private infoService: InfoService,
    private imageService: ImageService,
    private router: Router) { }

  ngOnInit(): void {
    this.getProducts();
  }

  private getProducts() {
    this.productService.getProductList(this.type).subscribe(data => {
      this.products = data;
      this.products.forEach(product => {
        product.price = new Price();

        this.priceService.getPriceByID(product.id).subscribe( data => {
          product.price = data;
          console.log('Preis für folgendes Produkt wurde erfolgreich geladen', data)
        }, error => {
         // console.error("Fehler beim laden des Preises von folgendem Produkt", product, error)
        });
      });
    });


  }

  updateProduct(id: number) {
    this.router.navigate(['update-product', id]);
  }

  deleteProduct(id: number) {
    this.priceService.deletePriceByProductID(id).subscribe ( data => {
      console.log('Preis des Productes wurde erfolgreich gelöscht', data);

      this.productService.deletePoduct(id).subscribe( data => {
        console.log('Poduct wurde gelöscht', data)
        this.getProducts();
      });

    }, error => {
      console.error('Preis des Produktes konnte nicht gelöscht werden', error)
    });


  }

  productDetails(id: number) {
    this.router.navigate(['product-details', id]);
  }
}

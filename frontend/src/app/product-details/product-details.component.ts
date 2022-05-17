import { PriceService } from './../services/price.service';
import { ProductService } from './../services/product.service';
import { ActivatedRoute } from '@angular/router';
import { ProductComplete } from './../model/productComplete';
import { Component, OnInit } from '@angular/core';
import { ImageService } from '../services/image.service';
import { InfoService } from '../services/info.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  productComplete!: ProductComplete;
  isImageSaved: boolean = false;

  constructor(private route: ActivatedRoute,
     private priceService: PriceService,
     private infoService: InfoService,
     private imageService: ImageService,
     private productService: ProductService) { }

  ngOnInit(): void {
    this.productComplete = new ProductComplete();
    this.productComplete.id = this.route.snapshot.params['id'];

    this.productService.getProductById(this.productComplete.id).subscribe( data => {
      this.productComplete.id = data.id;
      this.productComplete.name = data.name;
      this.productComplete.productType = data.productType;
    });

    this.priceService.getPriceByID(this.productComplete.id).subscribe( data => {
      this.productComplete.price = data;
    });

    this.imageService.getImageById(this.productComplete.id).subscribe( data => {
      this.productComplete.image = data[0];
      console.log("image mit base 64 string wurde geladen", data);
      this.isImageSaved = true;
    });

    this.infoService.getInfoById(this.productComplete.id).subscribe( data => {
      this.productComplete.info = data;
    });

  }

}

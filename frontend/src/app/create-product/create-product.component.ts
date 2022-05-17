import { Image as ImageModell } from './../model/image';
import { Price } from './../model/price';
import { ProductComplete } from './../model/productComplete';
import { PriceService } from './../services/price.service';
import { ProductService } from '../services/product.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { InfoService } from '../services/info.service';
import { ImageService } from '../services/image.service';
import { Info } from '../model/info';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {

  isImageSaved: boolean = false;
  productComplete: ProductComplete = new ProductComplete();

  constructor(private productService: ProductService,
    private priceService: PriceService,
    private infoService: InfoService,
    private imageService: ImageService,
    private router: Router) { }

  ngOnInit(): void {
    this.productComplete.price = new Price();
    this.productComplete.image = new ImageModell();
    this.productComplete.info = new Info();
  }

  fileChangeEvent(fileInput: any) {
    if (fileInput.target.files && fileInput.target.files[0]) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const image = new Image();
        image.src = e.target.result;
        image.onload = rs => {
          const imgBase64Path = e.target.result;
          this.productComplete.image.content = imgBase64Path;
          this.isImageSaved = true;
          console.log("generated base 64 String", imgBase64Path);
        };
      };
      reader.readAsDataURL(fileInput.target.files[0]);
    }
  }

  handleReaderLoaded(readerEvt: any) {
    var binaryString = readerEvt.target.result;
    this.productComplete.image.content = btoa(binaryString);
  }



  saveProduct() {
    this.productService.createProduct(this.productComplete).subscribe( data => {
      console.log("Product saved. ", data);

      this.priceService.savePrice(this.productComplete.price.price, data.id, this.productComplete.price.isDiscount).subscribe ( data => {
        console.log('Speichern des Preises erfolgreich', data);
      }, error => console.log("Fehler beim speichern des Preises", error));

      this.infoService.saveInfo(this.productComplete.info, data.id).subscribe( data => {
        console.log('Speichern der Info erfolgreich', data);
      }, error => console.log('Fehler beim Speichern der Info', error));

      this.imageService.saveImage(this.productComplete.image, data.id).subscribe( data => {
        console.log('Speichern des Image erfolgreich', data);
      }, error => console.log('Fehler beim Speichern des Image', error));

      this.goToProductList();
    }, error => console.log("error beim Speichern des Produktes", error));
  }

  goToProductList() {
    this.router.navigate(['/products']);
  }

  onSubmit() {
    console.log("trying to save", this.productComplete)
    this.saveProduct();
  }

  onDiscountChanged(value: boolean) {
    this.productComplete.price.isDiscount = value;
  }

}

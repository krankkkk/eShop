import { ProductService } from './../services/product.service';
import { Component} from '@angular/core';

@Component({
  selector: 'app-product-tab',
  templateUrl: './product-tab.component.html',
  styleUrls: ['./product-tab.component.css']
})
export class ProductTabComponent {

  types!: String[];

   // injecten des Service
   constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProductTypes();
  }

  private getProductTypes() {
    this.productService.getTypes().subscribe( data => {
      console.log("Typen wurden gefüllt")
      this.types = data;
    });
  }
}

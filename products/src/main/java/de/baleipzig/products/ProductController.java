package de.baleipzig.products;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService service) {
        this.productService = service;
    }

    @GetMapping("/products")
    public List<Long> all(){
        return productService.getAllProducts();
    }

    @PostMapping(value = "/products", consumes = "application/json")
    public Long newProduct(@RequestBody @Valid ProductDTO dtoProduct) {
        return productService.saveProduct(dtoProduct);
    }

    @GetMapping("/products/{id}")
    public ProductDTO one(@PathVariable long id){
        return productService.getOneProduct(id);
    }

    /**
     * Updated ein Produkt.
     * @param dtoProduct Die neuen Daten für das Produkt
     * @param id die ID des Produktes, das bearbeitet werden soll
     * @return die ID des Updateten Produkt
     */
    @PutMapping(value = "/products/{id}", consumes = "application/json", produces = "application/json")
    public Long replaceProduct(@RequestBody @Valid ProductDTO dtoProduct, @PathVariable Long id) {
        return productService.updateProduct(dtoProduct,id);
    }


    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }



}

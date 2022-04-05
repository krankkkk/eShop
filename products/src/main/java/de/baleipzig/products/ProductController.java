package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductDatabaseDummy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller sitzt auf Port 80
@RestController
public class ProductController {

    private final ProductDatabaseDummy databaseDummy;

    public ProductController() {
        this.databaseDummy = new ProductDatabaseDummy();
    }

    @GetMapping("/products")
    public List<Product> all(){
        return databaseDummy.getAll();
    }

    // return value, um evtl. ein nicht erfolgreichen adden sichtbar wird.
    @PostMapping(value = "/products", consumes = "application/json", produces = "application/json")
    public Product newProduct(@RequestBody Product newProduct) {
        return databaseDummy.save(newProduct);
    }

    @GetMapping("/products/{id}")
    public Product one(@PathVariable long id){
        return databaseDummy.get(id).orElse(null);
    }

    @PutMapping(value ="/products", consumes = "application/json", produces = "application/json")
    public Product replaceProduct (@RequestBody Product product) {
        return databaseDummy.update(product);
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct (@PathVariable long id) {
        databaseDummy.delete(id);
    }

}

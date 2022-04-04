package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductDatabaseDummy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/products")
    public void newProduct(@RequestBody Product newProduct) {
        databaseDummy.save(newProduct);
    }

    @GetMapping("/products/{id}")
    public Product one(@PathVariable long id){
        return databaseDummy.get(id).orElse(null);
    }

    @PutMapping("/products/{id}")
    public void replaceProduct (@RequestBody Product product, @PathVariable long id) {
        databaseDummy.update(id, product);
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct (@PathVariable long id) {
        databaseDummy.delete(id);
    }

}

package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductNotFoundException;
import de.baleipzig.products.persistance.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    public List<Product> all(){
        return repository.findAll();
    }

    // return value, um evtl. ein nicht erfolgreichen adden sichtbar wird.
    @PostMapping(value = "/products", consumes = "application/json", produces = "application/json")
    public Product newProduct(@RequestBody Product newProduct) {
        return repository.save(newProduct);
    }

    @GetMapping("/products/{id}")
    public Product one(@PathVariable long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping(value = "/products/{id}", consumes = "application/json", produces = "application/json")
    public Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {

        return repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setProductType(newProduct.getProductType());
                    product.setEigenschaft(newProduct.getEigenschaft());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
    }


    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }

}

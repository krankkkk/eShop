package de.baleipzig.products.rest;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.mapping.MapperFactory;
import de.baleipzig.products.mapping.MapperFactoryImplementation;
import de.baleipzig.products.persistance.Product;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService service) {
        this.productService = service;
    }

    @GetMapping("/")
    public List<Long> all() {
        return productService.getAllProducts();
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Long newProduct(@RequestBody @Valid ProductDTO dtoProduct) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public ProductDTO one(@PathVariable long id) {
        return mapper.convertToDTO(productService.getOneProduct(id));
    }

    /**
     * Updated ein Produkt.
     *
     * @param dtoProduct Die neuen Daten für das Produkt
     * @param id         die ID des Produktes, das bearbeitet werden soll
     * @return die ID des Updateten Produkt
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Long replaceProduct(@RequestBody @Valid ProductDTO dtoProduct, @PathVariable Long id) {
        return productService.updateProduct(mapper.mapProduct(dtoProduct), id);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


}

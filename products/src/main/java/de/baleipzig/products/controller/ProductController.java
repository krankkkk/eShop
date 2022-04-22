package de.baleipzig.products.controller;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.services.interfaces.MapperService;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.services.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
public class ProductController {

    private final ProductService productService;
    private final MapperService mapper;

    public ProductController(ProductService service, MapperService mapper) {
        this.productService = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public List<Long> all() {
        return productService.getAllProducts();
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Long newProduct(@RequestBody @Valid ProductDTO dtoProduct) {
        Product product = mapper.mapProduct(dtoProduct);
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
     * @return die ID des zu updateten Produkt
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Long replaceProduct(@RequestBody @Valid ProductDTO dtoProduct, @PathVariable @NotNull Long id) {
        return productService.updateProduct(mapper.mapProduct(dtoProduct, id));
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable @NotNull Long id) {
        productService.deleteProduct(id);
    }
}

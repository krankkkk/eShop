package de.baleipzig.products.rest;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.mapping.MapperFactory;
import de.baleipzig.products.persistance.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ProductController {

    private final ProductService productService;
    private final MapperFactory mapper;

    public ProductController(ProductService service, MapperFactory mapper) {
        this.productService = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public List<Long> all() {
        return productService.getAllProducts();
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
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
     * @return die ID des Updateten Produkt
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long replaceProduct(@RequestBody @Valid ProductDTO dtoProduct, @PathVariable Long id) {
        return productService.updateProduct(mapper.mapProduct(dtoProduct), id);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


}

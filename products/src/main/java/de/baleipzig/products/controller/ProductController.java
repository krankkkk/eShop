package de.baleipzig.products.controller;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.exceptions.InvalidArgumentException;
import de.baleipzig.products.services.interfaces.MapperService;
import de.baleipzig.products.services.interfaces.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
public class ProductController {

    private static final int PAGE_SIZE = 10;
    private final ProductService productService;
    private final MapperService mapper;

    public ProductController(ProductService service, MapperService mapper) {
        this.productService = service;
        this.mapper = mapper;
    }

    @GetMapping("/get")
    public List<ProductDTO> all(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam("type") @NotNull ProductType type) {

        if (page <= 0) {
            throw new InvalidArgumentException("Page cannot be less or equal than 0");
        }

        return productService.getProducts(type, (page - 1) * 10, PAGE_SIZE)
                .stream()
                .map(this.mapper::convertToDTO)
                .toList();
    }

    @PutMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO newProduct(@RequestBody @Valid ProductDTO dtoProduct) {
        Product toSave = mapper.mapProduct(dtoProduct);
        Product savedProduct = productService.saveNewProduct(toSave);
        return mapper.convertToDTO(savedProduct);
    }

    @GetMapping("/get/{id}")
    public ProductDTO get(@PathVariable long id) {
        return mapper.convertToDTO(productService.getByID(id));
    }

    /**
     * Updated ein Produkt.
     *
     * @param dtoProduct Die neuen Daten für das Produkt
     * @return Das gespeicherte Object
     */
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO replaceProduct(@RequestBody @Valid ProductDTO dtoProduct) {
        Product newProduct = mapper.mapProduct(dtoProduct);
        Product updatedProduct = productService.updateProduct(newProduct);
        return mapper.convertToDTO(updatedProduct);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable @NotNull Long id) {
        productService.deleteByID(id);
    }

    @GetMapping("types")
    public List<ProductType> types() {
        return List.of(ProductType.values());
    }
}

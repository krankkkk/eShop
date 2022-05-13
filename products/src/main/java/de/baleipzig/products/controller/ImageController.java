package de.baleipzig.products.controller;

import de.baleipzig.eshop.api.dto.ImageDTO;
import de.baleipzig.products.entities.Image;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.services.interfaces.ImageService;
import de.baleipzig.products.services.interfaces.MapperService;
import de.baleipzig.products.services.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/image")
public class ImageController {

    private final ProductService productService;
    private final ImageService imageService;
    private final MapperService mapperService;

    public ImageController(final ProductService productService,
                           final ImageService imageService,
                           final MapperService mapperService) {
        this.productService = productService;
        this.imageService = imageService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public Collection<ImageDTO> get(@RequestParam("productID") final long productID) {
        Product product = this.productService.getByID(productID);

        return this.imageService.getImagesByProduct(product)
                .stream()
                .map(this.mapperService::toDTO)
                .toList();

    }

    @DeleteMapping
    public void delete(@RequestParam("imageID") final long imageID) {
        final Image image = this.imageService.getImageByID(imageID);

        this.imageService.deleteImage(image);
    }

    @PostMapping
    public ImageDTO create(@Valid @RequestBody final ImageDTO dto) {
        final Image toSave = this.mapperService.toEntity(dto);
        final Image saved = this.imageService.newImage(toSave);
        return this.mapperService.toDTO(saved);
    }

}

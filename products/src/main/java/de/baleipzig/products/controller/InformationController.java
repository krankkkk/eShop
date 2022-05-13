package de.baleipzig.products.controller;

import de.baleipzig.eshop.api.dto.InformationDTO;
import de.baleipzig.products.entities.Information;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.services.interfaces.InformationService;
import de.baleipzig.products.services.interfaces.MapperService;
import de.baleipzig.products.services.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/info")
public class InformationController {

    private final ProductService productService;
    private final InformationService informationService;
    private final MapperService mapperService;

    public InformationController(final ProductService productService,
                                 final InformationService informationService,
                                 final MapperService mapperService) {
        this.productService = productService;
        this.informationService = informationService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public InformationDTO get(@RequestParam("productID") final long productID) {
        Product product = this.productService.getByID(productID);

        Information information = this.informationService.getInformationByProduct(product);
        return this.mapperService.toDTO(information);

    }

    @DeleteMapping
    public void delete(@RequestParam("imageID") final long imageID) {
        final Information information = this.informationService.getInformationByID(imageID);

        this.informationService.deleteInformation(information);
    }

    @PostMapping
    public InformationDTO create(@NotNull @RequestBody final InformationDTO dto) {
        final Information toSave = this.mapperService.toEntity(dto);
        final Information saved = this.informationService.newInformation(toSave);
        return this.mapperService.toDTO(saved);
    }


}

package de.baleipzig.prices.controller;

import de.baleipzig.eshop.api.dto.PriceDTO;
import de.baleipzig.prices.services.interfaces.BasicPriceService;
import de.baleipzig.prices.services.interfaces.DiscountPriceService;
import de.baleipzig.prices.services.interfaces.MapperService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;

@RestController
public class PriceController {

    private final MapperService mapperService;
    private final BasicPriceService basicPriceService;
    private final DiscountPriceService discountPriceService;

    public PriceController(final MapperService mapperService,
                           final BasicPriceService basicPriceService,
                           final DiscountPriceService discountPriceService) {
        this.mapperService = mapperService;
        this.basicPriceService = basicPriceService;
        this.discountPriceService = discountPriceService;
    }

    @GetMapping
    public PriceDTO getPrice(@RequestParam("productID") final long productID,
                             @RequestParam(name = "at", required = false) OffsetDateTime at) {
        at = at == null ? OffsetDateTime.now() : at;

        if (this.discountPriceService.hasPrice(productID, at)) {
            return this.mapperService.toDTO(this.discountPriceService.getPriceByProductID(productID, at));
        }

        return this.mapperService.toDTO(this.basicPriceService.getPriceByProductID(productID, at));
    }


    @PostMapping
    public PriceDTO save(@RequestBody @Valid PriceDTO toSave) {
        if (toSave.isDiscount()) {
            return this.mapperService.toDTO(this.discountPriceService.savePrice(this.mapperService.toDP(toSave)));
        }

        return this.mapperService.toDTO(this.basicPriceService.savePrice(this.mapperService.toBP(toSave)));
    }

    @DeleteMapping
    public void deleteByDTO(@RequestBody @Valid PriceDTO toDelete) {
        if (toDelete.isDiscount()) {
            this.discountPriceService.deletePrice(this.mapperService.toDP(toDelete));
            return;
        }

        this.basicPriceService.deletePrice(this.mapperService.toBP(toDelete));
    }
    @DeleteMapping("product/{id}")
    public void deleteByProductID(@PathVariable("id") final long productID) {
       this.basicPriceService.deleteByProductID(productID);
       this.discountPriceService.deleteByProductID(productID);
    }


}

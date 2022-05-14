package de.baleipzig.prices.services;

import de.baleipzig.eshop.api.dto.PriceDTO;
import de.baleipzig.prices.entities.BasicPrice;
import de.baleipzig.prices.entities.DiscountPrice;
import de.baleipzig.prices.services.interfaces.MapperService;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {
    @Override
    public PriceDTO toDTO(BasicPrice price) {
        return new PriceDTO(price.getId(), price.getProductID(), price.getPrice(), price.getStart(), price.getEnd(), false);
    }

    @Override
    public PriceDTO toDTO(DiscountPrice price) {
        return new PriceDTO(price.getId(), price.getProductID(), price.getPrice(), price.getStart(), price.getEnd(), true);
    }

    @Override
    public BasicPrice toBP(PriceDTO dto) {
        if (dto.isDiscount()) {
            throw new IllegalArgumentException("Attempted to convert Discount to Basic Price " + dto);
        }

        return new BasicPrice(dto.priceID(), dto.productID(), dto.start(), dto.end(), dto.price());
    }

    @Override
    public DiscountPrice toDP(PriceDTO dto) {
        if (!dto.isDiscount()) {
            throw new IllegalArgumentException("Attempted to convert Basic Price to Discount " + dto);
        }

        return new DiscountPrice(dto.priceID(), dto.productID(), dto.start(), dto.end(), dto.price());
    }
}

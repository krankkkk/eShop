package de.baleipzig.prices.services.interfaces;

import de.baleipzig.eshop.api.dto.PriceDTO;
import de.baleipzig.prices.entities.BasicPrice;
import de.baleipzig.prices.entities.DiscountPrice;

public interface MapperService {

    PriceDTO toDTO(BasicPrice price);

    PriceDTO toDTO(DiscountPrice price);

    BasicPrice toBP(PriceDTO dto);

    DiscountPrice toDP(PriceDTO dto);
}

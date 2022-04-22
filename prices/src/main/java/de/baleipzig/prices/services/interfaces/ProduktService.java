package de.baleipzig.prices.services.interfaces;

import de.baleipzig.prices.entities.BasicPrice;
import de.baleipzig.prices.entities.DiscountPrice;

public interface ProduktService {

    long saveDiscountPrice(DiscountPrice discountPrice);

    long saveBasicPrice(BasicPrice basicPrice);

    DiscountPrice getDiscountByProductID(long id);

    BasicPrice getBasicPriceByProductID(long id);

}

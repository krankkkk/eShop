package de.baleipzig.prices;

import de.baleipzig.prices.persistance.basicPrice.BasicPrice;
import de.baleipzig.prices.persistance.discountPrice.DiscountPrice;

public interface ProduktserviceInterface {

    Long saveDiscountPrice(DiscountPrice discountPrice);

    Long saveBasicPrice(BasicPrice basicPrice);

    DiscountPrice getOneDiscountPrice(Long id);

    BasicPrice getOneBasicPrice(Long id);


}

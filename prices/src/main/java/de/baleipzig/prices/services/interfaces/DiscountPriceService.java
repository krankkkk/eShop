package de.baleipzig.prices.services.interfaces;

import de.baleipzig.prices.entities.DiscountPrice;

import java.time.OffsetDateTime;

public interface DiscountPriceService extends PriceService<DiscountPrice> {

    boolean hasPrice(long productID, OffsetDateTime at);
}

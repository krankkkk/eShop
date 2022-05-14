package de.baleipzig.prices.services.interfaces;

import de.baleipzig.prices.entities.Price;

import java.time.OffsetDateTime;

public interface PriceService<T extends Price> {

    T getPriceByID(long priceID);

    T getPriceByProductID(long productID, OffsetDateTime at);

    T savePrice(T price);

    void deletePrice(T price);
}

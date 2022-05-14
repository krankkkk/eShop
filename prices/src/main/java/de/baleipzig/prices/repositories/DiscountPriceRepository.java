package de.baleipzig.prices.repositories;

import de.baleipzig.prices.entities.DiscountPrice;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountPriceRepository extends AbstractPriceRepository<DiscountPrice> {
}

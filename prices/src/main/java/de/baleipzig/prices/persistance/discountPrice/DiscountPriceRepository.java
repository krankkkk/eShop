package de.baleipzig.prices.persistance.discountPrice;

import de.baleipzig.prices.persistance.discountPrice.DiscountPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPriceRepository extends JpaRepository<DiscountPrice, Long> {


}

package de.baleipzig.prices.persistance.basicPrice;

import de.baleipzig.prices.persistance.basicPrice.BasicPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicPriceRepository extends JpaRepository<BasicPrice, Long>{

}



package de.baleipzig.prices.repositories;

import de.baleipzig.prices.entities.BasicPrice;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface BasicPriceRepository extends AbstractPriceRepository<BasicPrice> {
}



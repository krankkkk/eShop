package de.baleipzig.prices.repositories;


import de.baleipzig.prices.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.OffsetDateTime;
import java.util.Optional;

@NoRepositoryBean
interface AbstractPriceRepository<T extends Price> extends JpaRepository<T, Long> {

    Optional<T> findByEndAfterAndStartBeforeAndProductID(OffsetDateTime end, OffsetDateTime start, Long productID);

}

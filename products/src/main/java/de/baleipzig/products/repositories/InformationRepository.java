package de.baleipzig.products.repositories;

import de.baleipzig.products.entities.Information;
import de.baleipzig.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {

    Optional<Information> getInformationByProduct(Product product);

    @Transactional
    void deleteAllByProduct(Product product);
}

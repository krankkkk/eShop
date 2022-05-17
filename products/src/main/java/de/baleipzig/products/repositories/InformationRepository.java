package de.baleipzig.products.repositories;

import de.baleipzig.products.entities.Information;
import de.baleipzig.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {

    Optional<Information> getInformationByProduct(Product product);

    void deleteAllByProduct(Product product);
}

package de.baleipzig.products.repositories;

import de.baleipzig.products.entities.Image;
import de.baleipzig.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Transactional(readOnly = true)
    List<Image> getImagesByProduct(Product product);
}

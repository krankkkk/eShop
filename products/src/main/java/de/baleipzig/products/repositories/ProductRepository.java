package de.baleipzig.products.repositories;

import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Stream<Product> streamAllByProductType(ProductType productType, Sort sort);
}

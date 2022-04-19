package de.baleipzig.products.mapping;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.persistance.Product;

public interface MapperFactory {

    Product mapProduct(ProductDTO toMap);

    ProductDTO convertToDTO(Product product);
}

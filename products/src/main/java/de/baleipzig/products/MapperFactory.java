package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;

public interface MapperFactory {

    Product mapProduct( ProductDTO toMap);
}

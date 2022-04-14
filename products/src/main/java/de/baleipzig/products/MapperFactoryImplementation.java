package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductType;

public class MapperFactoryImplementation implements MapperFactory{


    @Override
    public Product mapProduct(ProductDTO toMap) {
        return new Product(ProductType.valueOf(toMap.productType().toUpperCase()), toMap.name(), toMap.property());
    }
}

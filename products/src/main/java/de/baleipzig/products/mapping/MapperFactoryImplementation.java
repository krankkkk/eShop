package de.baleipzig.products.mapping;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.persistance.Product;
import de.baleipzig.eshop.api.foo.ProductType;

public class MapperFactoryImplementation implements MapperFactory {


    @Override
    public Product mapProduct(ProductDTO toMap) {
        return new Product(ProductType.valueOf(toMap.productType().toUpperCase()), toMap.name(), toMap.property());
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getProductType().name(),
                product.getName(),
                product.getProperty());
    }
}

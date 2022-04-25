package de.baleipzig.products.services;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;
import de.baleipzig.products.services.interfaces.MapperService;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {

    @Override
    public Product mapProduct(ProductDTO toMap) {
        return new Product(
                toMap.id(),
                ProductType.valueOf(toMap.productType().toUpperCase()),
                toMap.name());
    }


    @Override
    public ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getProductType().name(),
                product.getName());
    }
}

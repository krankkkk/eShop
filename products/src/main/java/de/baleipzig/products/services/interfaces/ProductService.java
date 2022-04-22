package de.baleipzig.products.services.interfaces;

import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;

import java.util.List;

public interface ProductService {

    List<Long> getProducts(ProductType type, int offset, int maxCount);

    List<Long> getAllProducts();

    Long saveProduct(Product product);

    Product getOneProduct(Long id);

    Long updateProduct(Product newProduct);

    void deleteProduct(Long id);

}

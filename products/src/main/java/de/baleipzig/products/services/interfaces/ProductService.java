package de.baleipzig.products.services.interfaces;

import de.baleipzig.products.entities.Product;

import java.util.List;

public interface ProductService {

    List<Long> getAllProducts();

    Long saveProduct(Product product);

    Product getOneProduct(Long id);

    Long updateProduct(Product newProduct);

    void deleteProduct(Long id);

}

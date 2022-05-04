package de.baleipzig.products.services.interfaces;

import de.baleipzig.eshop.api.enums.ProductType;
import de.baleipzig.products.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductType type, int offset, int maxCount);

    Product saveNewProduct(Product product);

    Product getByID(long productID);

    Product updateProduct(Product newProduct);

    void deleteByID(long id);

}

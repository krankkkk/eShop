package de.baleipzig.products.errorhandling;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Could not find Product " + id);
    }
}

package de.baleipzig.products;

import de.baleipzig.products.persistance.ProductType;

public record ProductDTO (long id, ProductType productType, String name, String property) {

}

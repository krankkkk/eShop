package de.baleipzig.products.services.interfaces;

import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.entities.Product;

/**
 * Ein Factory interface, welches DTO-Objecte zu Entites mappen soll und vice versa.
 */
public interface MapperService {

    /**
     * Mappt ein Produkt-DTO zu einer neuen Entity.
     *
     * @param toMap DTO welches zu mappen ist
     * @return Das passende Entity Object zu dem DTO oder ein neues Entity
     */
    Product mapProduct(ProductDTO toMap);

    /**
     * Mappt ein Produkt-DTO zu einer neuen Entity, setzt zusätzlich eine ID
     *
     * @param toMap DTO welches zu mappen ist.
     * @param id    ID welche am Product zu setzen ist.
     * @return Das passende Entity Object zu dem DTO oder ein neues Entity
     */
    Product mapProduct(ProductDTO toMap, Long id);

    /**
     * Mappt ein Product-Entity zu einem DTO-Object.
     *
     * @param product Die Entity welche gemappt werden soll.
     * @return Das passende DTO zur Entity.
     */
    ProductDTO convertToDTO(Product product);
}

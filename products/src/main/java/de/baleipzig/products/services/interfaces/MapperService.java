package de.baleipzig.products.services.interfaces;

import de.baleipzig.eshop.api.dto.ImageDTO;
import de.baleipzig.eshop.api.dto.InformationDTO;
import de.baleipzig.eshop.api.dto.ProductDTO;
import de.baleipzig.products.entities.Image;
import de.baleipzig.products.entities.Information;
import de.baleipzig.products.entities.Product;

/**
 * Ein Factory interface, welches DTO-Objecte zu Entities mappen soll und vice versa.
 */
public interface MapperService {

    /**
     * Mappt ein Produkt-DTO zu einer neuen Entity.
     *
     * @param toMap DTO welches zu mappen ist
     * @return Das passende Entity Object zu dem DTO oder ein neues Entity
     */
    Product toEntity(ProductDTO toMap);

    /**
     * Mappt ein Product-Entity zu einem DTO-Object.
     *
     * @param product Die Entity welche gemappt werden soll.
     * @return Das passende DTO zur Entity.
     */
    ProductDTO toDTO(Product product);

    ImageDTO toDTO(Image image);

    Image toEntity(ImageDTO image);


    InformationDTO toDTO(Information entity);

    Information toEntity(InformationDTO dto);
}

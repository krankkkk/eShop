package de.baleipzig.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception welche signalisieren soll, das ein Produkt mit einer gegebenen ID nicht existiert
 */
public class ProductNotFoundException extends ResponseStatusException {

    public ProductNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Could not find Product with id %d".formatted(id));
    }
}

package de.baleipzig.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception welche signalisieren soll, das ein Bild mit einer gegebenen ID nicht existiert
 */
public class ImageNotFoundException extends ResponseStatusException {

    public static String NULL_REASON = "An Image cannot have <null> as id";

    public ImageNotFoundException(final String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public ImageNotFoundException(final long id) {
        this("Could not find Image with id %d".formatted(id));
    }
}

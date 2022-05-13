package de.baleipzig.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception welche signalisieren soll, das ein Information-Object mit einer gegebenen ID nicht existiert
 */
public class InformationNotFoundException extends ResponseStatusException {

    public static String NULL_REASON = "An Information cannot have <null> as id";

    public InformationNotFoundException(final String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public InformationNotFoundException(final long id) {
        this("Could not find Information with id %d".formatted(id));
    }
}

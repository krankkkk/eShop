package de.baleipzig.prices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PriceNotFoundException extends ResponseStatusException {

    public PriceNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "Der Preis %d wurde nicht gefunden".formatted(id));
    }
}

package de.baleipzig.prices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoPriceAvailableException extends ResponseStatusException {
    public NoPriceAvailableException(final long productID) {
        super(HttpStatus.NOT_FOUND, "No Price found for product with id %d".formatted(productID));
    }
}

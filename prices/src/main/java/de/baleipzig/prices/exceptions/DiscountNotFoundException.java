package de.baleipzig.prices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DiscountNotFoundException extends ResponseStatusException {

    public DiscountNotFoundException(final long productID) {
        super(HttpStatus.NOT_FOUND, "No Discount found for product with id %d".formatted(productID));
    }
}

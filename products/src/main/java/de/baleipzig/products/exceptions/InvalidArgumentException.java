package de.baleipzig.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidArgumentException extends ResponseStatusException {
    public InvalidArgumentException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

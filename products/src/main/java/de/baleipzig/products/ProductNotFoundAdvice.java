package de.baleipzig.products;


import de.baleipzig.products.persistance.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// diese Klasse rendert die HTTP 404 Fehlermeldung falls eine ProductNotFoundException auftritt
@ControllerAdvice
public class ProductNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFoundHandler( ProductNotFoundException ex) {
        return ex.getMessage();
    }
}

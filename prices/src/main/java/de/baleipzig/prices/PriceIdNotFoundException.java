package de.baleipzig.prices;

public class PriceIdNotFoundException extends RuntimeException {

    public PriceIdNotFoundException ( Long id ){
        super("Der Preis wurde nicht gefunden");
    }
}

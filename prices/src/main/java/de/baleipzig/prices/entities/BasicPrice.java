package de.baleipzig.prices.entities;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name = "prices")
@Entity
public class BasicPrice extends Price {
    public BasicPrice() {
        this(null, null, null, -1);
    }

    public BasicPrice(Long productID, OffsetDateTime start, OffsetDateTime end, int price) {
        this(null, productID, start, end, price);
    }

    public BasicPrice(Long priceID, Long productID, OffsetDateTime start, OffsetDateTime end, int price) {
        super(priceID, productID, start, end, price);
    }

    @Override
    public String toString() {
        return "BasicPrice{productID='%d', start=%s, end='%s', basicPrice='%d'}".formatted(getProductID(), getStart(), getEnd(), getPrice());
    }
}

package de.baleipzig.prices.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name = "discounts")
@Entity
public class DiscountPrice extends Price {
    public DiscountPrice() {
        this(null, null, null, -1);
    }

    public DiscountPrice(Long productID, OffsetDateTime start, OffsetDateTime end, int price) {
        this(null, productID, start, end, price);
    }

    public DiscountPrice(Long priceID, Long productID, OffsetDateTime start, OffsetDateTime end, int price) {
        super(priceID, productID, start, end, price);
    }

    @Override
    public String toString() {
        return "DiscountPrice{productID=%d, start=%s, end=%s, price=%d}".formatted(getProductID(), getStart(), getEnd(), getPrice());
    }
}

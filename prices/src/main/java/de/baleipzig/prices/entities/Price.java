package de.baleipzig.prices.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class Price extends AbstractPersistable<Long> {

    @Column(name = "productID", nullable = false)
    private Long productID;

    @Column(name = "startTime", nullable = false)
    private OffsetDateTime start;

    @Column(name = "endTime", nullable = false)
    private OffsetDateTime end;

    @Column(name = "price", nullable = false)
    private int price;

    protected Price(Long priceID, Long productID, OffsetDateTime start, OffsetDateTime end, int price) {
        setId(priceID);
        this.productID = productID;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public long getProductID() {
        return productID;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public int getPrice() {
        return price;
    }
}

package de.baleipzig.prices.persistance;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name = "prices")
@Entity
public class Price extends AbstractPersistable<Long> {

    @Column(name = "price_id", nullable = false)
    private long productID;

    @Column(name = "startTime", nullable = false)
    private OffsetDateTime start;

    @Column(name = "endTime", nullable = false)
    private OffsetDateTime end;

    @Column(name = "price", nullable = false)
    private int price;

    public Price() {
    }

    public Price(long productID, OffsetDateTime start, OffsetDateTime end, int price) {
        this.productID = productID;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "start=" + start +
                ", end='" + end + '\'' +
                ", price='" + price + '\'' +
                '}';
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

    public void setPrice(int price) {
        this.price = price;
    }
}

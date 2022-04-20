package de.baleipzig.prices.persistance.discountPrice;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name = "prices")
@Entity
public class DiscountPrice extends AbstractPersistable<Long> {

    @Column(name = "price_id", nullable = false)
    private long productID;

    @Column(name = "startTime", nullable = false)
    private OffsetDateTime start;

    @Column(name = "endTime", nullable = false)
    private OffsetDateTime end;

    @Column( name = "discountValue", nullable = false )
    private boolean discountValue;

    @Column(name = "price", nullable = false)
    private int price;

    public DiscountPrice() {
    }

    public DiscountPrice(long productID, OffsetDateTime start, OffsetDateTime end, int price) {
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

    public void setStart(OffsetDateTime start) {
        this.start = start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public void setEnd(OffsetDateTime end) {
        this.end = end;
    }

    public boolean getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(boolean discountValue) {
        this.discountValue = discountValue;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

package de.baleipzig.prices.persistance;

import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = "prices")
@Entity
public class Price extends AbstractPersistable <Long>{


    @JoinColumn(name = "price_id", referencedColumnName = "product_id")
    private int product_price_id;

    @Column(name = "start", nullable = false)
    private OffsetDateTime start;

    @Column(name = "end", nullable = false)
    private OffsetDateTime end;

    @Column(name = "discount", nullable = false)
    private boolean discountAvailable;

    @Column(name = "price", nullable = false)
    private int price;

    public Price (){}

    public Price(OffsetDateTime start, OffsetDateTime end, boolean discountAvailable, int price) {
        this.start = start;
        this.end = end;
        this.discountAvailable = discountAvailable;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "start=" + start +
                ", end='" + end+ '\'' +
                ", discount='" + discountAvailable + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public boolean isDiscountAvailable() {
        return discountAvailable;
    }

    public void setDiscountAvailable(boolean discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

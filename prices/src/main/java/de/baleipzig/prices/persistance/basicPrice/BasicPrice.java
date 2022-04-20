package de.baleipzig.prices.persistance.basicPrice;


import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name = "basicprices")
@Entity
public class BasicPrice extends AbstractPersistable<Long> {

    @Column(name=  "price_id", nullable = false)
    private long priceID;

    @Column( name = "startTime", nullable = false )
    private OffsetDateTime start;

    @Column( name = "endTime", nullable = false )
    private OffsetDateTime end;

    @Column ( name = "basicPrice", nullable = false )
    private int basicPrice;


    public BasicPrice (){}

    public BasicPrice( OffsetDateTime start, OffsetDateTime end, int basicPrice) {

        this.start = start;
        this.end = end;
        this.basicPrice = basicPrice;
    }

    @Override
    public String toString() {
        return "Price{" +
                "start=" + start +
                ", end='" + end + '\'' +
                ", basicPrice='" + basicPrice + '\'' +
                '}';
    }

    public long getPiceID() {
        return priceID;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public int getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice ( int newPrice )
    {
        this.basicPrice = newPrice;
    }
}

package de.baleipzig.products.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
public class Information extends AbstractPersistable<Long> {

    @JoinColumn(referencedColumnName = "id", name = "productID")
    @OneToOne(optional = false)
    private final Product product;

    @Column(name = "techdoc")
    private final String techDoc;

    @Column(name = "additional")
    private final String additionalInformation;


    public Information() {
        this(null, null, null, null);
    }

    public Information(final Product product,
                       final String techDoc,
                       final String additionalInformation) {
        this(null, product, techDoc, additionalInformation);
    }

    public Information(final Long id,
                       final Product product,
                       final String techDoc,
                       final String additionalInformation) {
        setId(id);
        this.product = product;
        this.techDoc = techDoc;
        this.additionalInformation = additionalInformation;
    }

    public Product getProduct() {
        return product;
    }

    public String getTechDoc() {
        return techDoc;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }
}

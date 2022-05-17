package de.baleipzig.products.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Table(name = "images", indexes = {@Index(columnList = "productID")})
public class Image extends AbstractPersistable<Long> {

    @JoinColumn(referencedColumnName = "id", name = "productID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private final Product product;

    @Column(name = "image", nullable = false)
    @Lob
    private final String imageData;

    public Image() {
        this(null, null, null);
    }

    public Image(final Product product,
                 final String imageData) {
        this(null, product, imageData);
    }

    public Image(final Long id,
                 final Product product,
                 final String imageData) {
        setId(id);
        this.product = product;
        this.imageData = imageData;
    }

    public Product getProduct() {
        return product;
    }

    public String getImageData() {
        return imageData;
    }

    @Override
    public String toString() {
        return "Image{id=%d,product=%s}".formatted(getId(), product);
    }
}

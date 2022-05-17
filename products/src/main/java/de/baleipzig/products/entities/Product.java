package de.baleipzig.products.entities;

import de.baleipzig.eshop.api.enums.ProductType;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "products")
@Entity
public class Product extends AbstractPersistable<Long> {

    @Column(name = "Type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private final ProductType productType;

    @Column(name = "name", nullable = false)
    private final String name;

    public Product() {
        this(null, null, null);
    }

    public Product(final ProductType productType, final String name) {
        this(null, productType, name);
    }

    public Product(final Long id, final ProductType productType, final String name) {
        setId(id);
        this.productType = productType;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return productType.equals(product.productType) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, name);
    }

    @Override
    public String toString() {
        return "Product{id=%d, productType=%s, name='%s}".formatted(getId(), productType, name);
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getName() {
        return name;
    }
}

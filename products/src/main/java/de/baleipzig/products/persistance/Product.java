package de.baleipzig.products.persistance;

import de.baleipzig.eshop.api.foo.ProductType;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "products")
@Entity
public class Product extends AbstractPersistable<Long> {

    @Column(name = "Type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @Column(name = "name", nullable = false)
    private String name;

    private String property;

    public Product() {
    }

    public Product(ProductType productType, String name, String property) {
        this.productType = productType;
        this.name = name;
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return productType.equals(product.productType) && Objects.equals(name, product.name) && Objects.equals(property, product.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, name, property);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType=" + productType +
                ", name='" + name + '\'' +
                ", eigenschaft='" + property + '\'' +
                '}';
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String eigenschaft) {
        this.property = eigenschaft;
    }
}

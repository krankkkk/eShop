package de.baleipzig.products.persistance;

import de.baleipzig.products.ProductDTO;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Locale;
import java.util.Objects;

@Table(name = "products")
@Entity
public class Product extends AbstractPersistable<Long> {

    @Column(name = "Type", nullable = false)
    private ProductType productType;

    @Column(name = "name", nullable = false)
    private String name;

    private String property;

    public Product() {
    }

    public Product(String productType, String name, String property) {
        this.productType = resolveProductType(productType);
        this.name = name;
        this.property = property;
    }

    public Product(ProductDTO productDTO) {
        this.productType = resolveProductType(productDTO.productType());
        this.name = productDTO.name();
        this.property = productDTO.property();
    }

    private ProductType resolveProductType (String stringType) {
        ProductType resolvedType;
        String type = stringType.toUpperCase(Locale.ROOT);
        switch (type) {
            case "HOUSEHOLD" -> resolvedType = ProductType.HOUSEHOLD;
            case "TEXTILES" -> resolvedType = ProductType.TEXTILES;
            case "FOOD" -> resolvedType = ProductType.FOOD;
            case "ELECTRONICS" -> resolvedType = ProductType.ELECTRONICS;
            default -> resolvedType = null;
        }
        return resolvedType;
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

package de.baleipzig.products.persistance;

import java.util.Objects;

public class Product {

    private ProductType productType;
    private long id;
    private String name;
    private String eigenschaft;

    public Product(ProductType productType, long id, String name, String eigenschaft) {
        this.productType = productType;
        this.id = id;
        this.name = name;
        this.eigenschaft = eigenschaft;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id && productType == product.productType && Objects.equals(name, product.name) && Objects.equals(eigenschaft, product.eigenschaft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, id, name, eigenschaft);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType=" + productType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", eigenschaft='" + eigenschaft + '\'' +
                '}';
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEigenschaft() {
        return eigenschaft;
    }

    public void setEigenschaft(String eigenschaft) {
        this.eigenschaft = eigenschaft;
    }
}

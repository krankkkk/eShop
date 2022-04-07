package de.baleipzig.products;

import de.baleipzig.products.persistance.Product;
import de.baleipzig.products.persistance.ProductType;

public class ProductDTO {

    private long id;
    private ProductType productType;
    private String name;
    private String eigenschaft;

    public ProductDTO (Product product) {
        this.id = product.getId();
        this.eigenschaft = product.getEigenschaft();
        this.name = product.getName();
        this.productType = product.getProductType();
    }

    public ProductDTO(long id, ProductType productType, String name, String eigenschaft) {
        this.id = id;
        this.productType = productType;
        this.name = name;
        this.eigenschaft = eigenschaft;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEigenschaft() {
        return eigenschaft;
    }

    public void setEigenschaft(String eigenschaft) {
        this.eigenschaft = eigenschaft;
    }
}

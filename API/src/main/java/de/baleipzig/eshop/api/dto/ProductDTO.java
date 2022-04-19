package de.baleipzig.eshop.api.dto;

import de.baleipzig.eshop.api.foo.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ProductDTO(@NotBlank @ValueOfEnum(enumClass = ProductType.class) String productType,
                         @NotBlank @Size(min = 2, message = "product Name should have at least 2 characters") String name,
                         String property) {

}
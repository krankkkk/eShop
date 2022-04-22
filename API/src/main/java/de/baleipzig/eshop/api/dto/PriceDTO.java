package de.baleipzig.eshop.api.dto;

import javax.validation.constraints.NotNull;

public record PriceDTO(Long priceID, @NotNull Long productID, int price, boolean isDiscount) {
}

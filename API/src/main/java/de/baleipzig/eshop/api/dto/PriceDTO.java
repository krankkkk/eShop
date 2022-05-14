package de.baleipzig.eshop.api.dto;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record PriceDTO(long priceID,
                       long productID,
                       int price,
                       @NotNull OffsetDateTime start,
                       @NotNull OffsetDateTime end,
                       boolean isDiscount) {
}

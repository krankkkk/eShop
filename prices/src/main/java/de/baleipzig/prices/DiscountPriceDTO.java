package de.baleipzig.prices;


import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record DiscountPriceDTO(@NotNull OffsetDateTime start, @NotNull OffsetDateTime end, boolean discountAvailable, int price){
}

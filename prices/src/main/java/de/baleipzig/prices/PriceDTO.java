package de.baleipzig.prices;


import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.OffsetDateTime;

public record PriceDTO(@NotNull OffsetDateTime start, @NotNull OffsetDateTime end, boolean discountAvailable, int price){
}

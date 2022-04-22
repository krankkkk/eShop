package de.baleipzig.prices;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record BasicPriceDTO(@NotNull OffsetDateTime start,@NotNull OffsetDateTime end,@NotNull int basicPrice)  {
}

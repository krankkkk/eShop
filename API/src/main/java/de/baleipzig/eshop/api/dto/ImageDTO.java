package de.baleipzig.eshop.api.dto;

import javax.validation.constraints.NotBlank;

public record ImageDTO(Long id, long productID, @NotBlank String content) {
}

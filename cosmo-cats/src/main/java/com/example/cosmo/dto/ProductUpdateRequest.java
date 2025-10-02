package com.example.cosmo.dto;

import com.example.cosmo.validation.CosmicWordCheck;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateRequest(
    @NotBlank @Size(min = 2, max = 100) @CosmicWordCheck
    String name,
    @NotBlank @Size(min = 5, max = 500)
    String description,
    @NotNull @DecimalMin(value = "0.01")
    BigDecimal price,
    @NotNull
    UUID categoryId
) {}

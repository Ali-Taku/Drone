package com.tasks.droneapi.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DroneMedicationDto(
        @NotNull @NotBlank Long drone,
        @NotNull @NotBlank Long medication,
        @NotNull @NotBlank int quantity
) {
}

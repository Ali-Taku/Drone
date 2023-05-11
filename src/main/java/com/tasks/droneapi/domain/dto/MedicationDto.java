package com.tasks.droneapi.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public record MedicationDto(
        @NotBlank @NotNull String name,
        @NotNull Float weight,
        @NotNull String code,
        @NotNull @NotBlank MultipartFile picture
){
}

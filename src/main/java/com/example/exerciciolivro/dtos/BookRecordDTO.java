package com.example.exerciciolivro.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookRecordDTO(
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String author,
        @NotBlank String genre,
        @NotNull Integer numberOfPages,
        @NotNull BigDecimal price
) {
}

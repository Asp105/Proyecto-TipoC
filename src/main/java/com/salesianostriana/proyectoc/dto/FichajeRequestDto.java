package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.model.Tipo;
import jakarta.validation.constraints.NotNull;

public record FichajeRequestDto(
        @NotNull(message = "{fichaje.tipo.obligatorio}")
        Tipo tipo
) {}
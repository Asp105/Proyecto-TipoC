package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.validation.PresupuestoSuficiente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@PresupuestoSuficiente
public record EditEmpleadoDto(
        Long empleadoId,
        Long departamentoId,

        @NotBlank(message = "El nombre completo es obligatorio")
        String nombreCompleto,

        @NotBlank(message = "El cargo es obligatorio")
        String cargo,

        @NotNull(message = "El salario es obligatorio")
        @PositiveOrZero(message = "El salario no puede ser negativo")
        BigDecimal salario
) {}
package com.salesianostriana.proyectoc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record EditEmpleadoDto(
        @NotBlank(message = "{empleado.nombre.obligatorio}")
        String nombreCompleto,

        @NotBlank(message = "{empleado.cargo.obligatorio}")
        String cargo,

        @NotNull(message = "{empleado.salario.obligatorio}")
        @PositiveOrZero(message = "{empleado.salario.no-negativo}")
        BigDecimal salario
) {}
package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.model.Empleado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record EditEmpleadoDto (
        @NotBlank(message = "") String nombreCompleto,
        @NotBlank(message = "") String cargo,
        @PositiveOrZero(message = "") BigDecimal salario
)  {
    public Empleado to() {
        return Empleado.builder()
                .nombreCompleto(nombreCompleto)
                .cargo(cargo)
                .salario(salario)
                .build();
    }
}

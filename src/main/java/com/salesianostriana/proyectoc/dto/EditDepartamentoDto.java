package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.model.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record EditDepartamentoDto (
    @NotBlank(message = "") String name,
    @PositiveOrZero(message = "") BigDecimal presupuesto
)  {
    public Departamento to() {
        return Departamento.builder()
                .name(name)
                .presupuesto(presupuesto)
                .build();
    }
}

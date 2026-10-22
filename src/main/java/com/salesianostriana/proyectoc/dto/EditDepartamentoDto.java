package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.model.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record EditDepartamentoDto (

    @NotBlank(message = "El nombre del departamento es obligatorio")
    String name,

    @NotNull(message = "El presupuesto es obligatorio")
    @PositiveOrZero(message = "El presupuesto no puede ser negativo")
    BigDecimal presupuesto
)  {
    public Departamento to() {
        return Departamento.builder()
                .name(name)
                .presupuesto(presupuesto)
                .build();
    }
}

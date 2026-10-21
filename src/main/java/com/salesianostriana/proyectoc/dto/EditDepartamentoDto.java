package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.model.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record EditDepartamentoDto (
    @NotBlank(message = "EL nombre es obligatorio") String name,
    @PositiveOrZero(message = "El presupuesto es obligatorio") BigDecimal presupuesto
)  {
    public Departamento to() {
        return Departamento.builder()
                .name(name)
                .presupuesto(presupuesto)
                .build();
    }
}

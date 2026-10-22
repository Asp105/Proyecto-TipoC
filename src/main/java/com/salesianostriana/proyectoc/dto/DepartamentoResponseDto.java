package com.salesianostriana.proyectoc.dto;

public class DepartamentoResponseDto {

import com.salesianostriana.proyectoc.model.Departamento;
import java.math.BigDecimal;

    public record DepartamentoResponseDto(
            Long id,
            String nombre,
            BigDecimal presupuesto
    ) {
        public static DepartamentoResponseDto from(Departamento d) {
            return new DepartamentoResponseDto(
                    d.getId(), d.getName(), d.getPresupuesto()
            );
        }
    }
}

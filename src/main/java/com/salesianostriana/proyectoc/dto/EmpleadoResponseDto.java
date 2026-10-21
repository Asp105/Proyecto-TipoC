package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.model.Empleado;
import java.math.BigDecimal;

public record EmpleadoResponseDto(
        Long id,
        String nombreCompleto,
        String cargo,
        BigDecimal salario,
        Long departamentoId,
        String departamentoNombre
) {
    public static EmpleadoResponseDto from(Empleado e) {
        return new EmpleadoResponseDto(
                e.getId(),
                e.getNombreCompleto(),
                e.getCargo(),
                e.getSalario(),
                e.getDepartamento() == null ? null : e.getDepartamento().getId(),
                e.getDepartamento() == null ? null : e.getDepartamento().getName()
        );
    }
}

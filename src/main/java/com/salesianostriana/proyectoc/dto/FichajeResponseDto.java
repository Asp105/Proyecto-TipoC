package com.salesianostriana.proyectoc.dto;

import com.salesianostriana.proyectoc.model.Fichaje;
import com.salesianostriana.proyectoc.model.Tipo;
import java.time.LocalDateTime;

public record FichajeResponseDto(Long id, LocalDateTime momento, Tipo tipo) {
    public static FichajeResponseDto from(Fichaje f) {
        return new FichajeResponseDto(f.getId(), f.getMomento(), f.getTipo());
    }
}

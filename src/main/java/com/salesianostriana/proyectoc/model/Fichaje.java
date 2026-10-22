package com.salesianostriana.proyectoc.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fichaje {

    @Id @GeneratedValue
    private Long id;
    private LocalDateTime momento;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @ToString.Exclude
    @ManyToOne
    private Empleado empleado;
}

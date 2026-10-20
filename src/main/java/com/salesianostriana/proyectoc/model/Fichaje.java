package com.salesianostriana.proyectoc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fichaje {

    @Id @GeneratedValue
    private Long id;
    private LocalDateTime momento;
    private Tipo tipo;

    @ManyToOne
    private Empleado nombre;
}

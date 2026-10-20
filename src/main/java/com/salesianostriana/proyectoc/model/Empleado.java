package com.salesianostriana.proyectoc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id @GeneratedValue
    private Long id;
    private String nombreCompleto;
    private String cargo;
    private BigDecimal salario;

    @ManyToOne
    private Departamento departamento;
}

package com.salesianostriana.proyectoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    private BigDecimal presupuesto;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "departamento")
    private List<Empleado> empleados;

}

package com.salesianostriana.proyectoc.utils;

import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.model.Empleado;
import com.salesianostriana.proyectoc.model.Fichaje;
import com.salesianostriana.proyectoc.model.Tipo;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import com.salesianostriana.proyectoc.repository.EmpleadoRepository;
import com.salesianostriana.proyectoc.repository.FichajeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final FichajeRepository fichajeRepository;

    @PostConstruct
    public void InitData() {

        Departamento d1 = Departamento.builder()
                .name("Desarrollo")
                .presupuesto(BigDecimal.valueOf(50000.00))
                .build();

        Departamento d2 = Departamento.builder()
                .name("Seguridad")
                .presupuesto(BigDecimal.valueOf(20000.00))
                .build();

        Departamento d3 = Departamento.builder()
                .name("RRHH")
                .presupuesto(BigDecimal.valueOf(20000.00))
                .build();

        departamentoRepository.saveAll(List.of(d1, d2, d3));

        Empleado e1 = Empleado.builder()
                .nombreCompleto("Guillermo Martín Carmona")
                .cargo("Jefe Máximo")
                .salario(BigDecimal.valueOf(5000.62))
                .departamento(d1)
                .build();

        Empleado e2 = Empleado.builder()
                .nombreCompleto("Álvaro Sánchez Pruna")
                .cargo("Jefe De Guillermo")
                .salario(BigDecimal.valueOf(8000.62))
                .departamento(d2)
                .build();

        Empleado e3 = Empleado.builder()
                .nombreCompleto("Juan Ruiz Campanario")
                .cargo("Atención al cliente")
                .salario(BigDecimal.valueOf(1000.30))
                .departamento(d3)
                .build();

        empleadoRepository.saveAll(List.of(e1, e2, e3));


        Fichaje f1 = Fichaje.builder()
                .empleado(e1)
                .momento(LocalDateTime.of(2026, 10, 23, 11, 48))
                .tipo(Tipo.ENTRADA)
                .build();

        Fichaje f2 = Fichaje.builder()
                .empleado(e2)
                .momento(LocalDateTime.of(2026, 10 ,23, 9, 50))
                .tipo(Tipo.ENTRADA)
                .build();

        Fichaje f3 = Fichaje.builder()
                .empleado(e3)
                .momento(LocalDateTime.of(2026, 10 ,20, 12, 50))
                .tipo(Tipo.ENTRADA)
                .build();

        fichajeRepository.saveAll(List.of(f1, f2, f3));
    }

}

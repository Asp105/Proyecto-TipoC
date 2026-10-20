package com.salesianostriana.proyectoc.repository;

import com.salesianostriana.proyectoc.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository
        extends JpaRepository<Empleado, Long> {}

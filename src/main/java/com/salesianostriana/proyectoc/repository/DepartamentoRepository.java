package com.salesianostriana.proyectoc.repository;

import com.salesianostriana.proyectoc.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository
        extends JpaRepository<Departamento, Long> {}

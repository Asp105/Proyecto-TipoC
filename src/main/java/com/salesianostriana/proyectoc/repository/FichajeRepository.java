package com.salesianostriana.proyectoc.repository;

import com.salesianostriana.proyectoc.model.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {
    List<Fichaje> findByEmpleadoIdOrderByMomentoDescIdDesc(Long empleadoId);
    Optional<Fichaje> findFirstByEmpleadoIdOrderByMomentoDescIdDesc(Long empleadoId);
    void deleteByEmpleadoId(Long empleadoId);
}
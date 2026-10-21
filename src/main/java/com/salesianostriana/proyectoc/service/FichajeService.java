package com.salesianostriana.proyectoc.service;

import com.salesianostriana.proyectoc.error.EmpleadoNoEncontradoException;
import com.salesianostriana.proyectoc.error.FichajeDuplicadoException;
import com.salesianostriana.proyectoc.model.Empleado;
import com.salesianostriana.proyectoc.model.Fichaje;
import com.salesianostriana.proyectoc.model.Tipo;
import com.salesianostriana.proyectoc.repository.EmpleadoRepository;
import com.salesianostriana.proyectoc.repository.FichajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FichajesService {

    private final FichajeRepository fichajeRepository;
    private final EmpleadoRepository empleadoRepository;

    public Fichaje fichar(Long empleadoId, Tipo tipoSolicitado) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(empleadoId));

        fichajeRepository
                .findFirstByEmpleadoIdOrderByMomentoDescIdDesc(empleadoId)
                .ifPresent(ultimo -> {
                    if (ultimo.getTipo() == tipoSolicitado) {
                        throw new FichajeDuplicadoException(
                                "No se pueden registrar dos fichajes del tipo "
                                        + tipoSolicitado + " seguidos"
                        );
                    }
                });

        Fichaje fichaje = Fichaje.builder()
                .empleado(empleado)
                .tipo(tipoSolicitado)
                .momento(LocalDateTime.now())
                .build();

        return fichajeRepository.save(fichaje);
    }

    @Transactional(readOnly = true)
    public List<Fichaje> getFichajesDeEmpleado(Long empleadoId) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new EmpleadoNoEncontradoException(empleadoId);
        }

        return fichajeRepository
                .findByEmpleadoIdOrderByMomentoDescIdDesc(empleadoId);
    }
}
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FichajesService {

    private final FichajeRepository fichajeRepository;
    private final EmpleadoRepository empleadoRepository;

    // Alterna automáticamente. El primer fichaje es ENTRADA.
    public Fichaje fichar(Long empleadoId) {
        Empleado empleado = buscarEmpleado(empleadoId);

        Optional<Fichaje> ultimo = fichajeRepository
                .findFirstByEmpleadoIdOrderByMomentoDescIdDesc(empleadoId);

        Tipo tipo = ultimo
                .map(fichaje -> fichaje.getTipo() == Tipo.ENTRADA
                        ? Tipo.SALIDA
                        : Tipo.ENTRADA)
                .orElse(Tipo.ENTRADA);

        return guardar(empleado, tipo);
    }

    // Si se proporciona un tipo, comprueba que no repita el último.
    public Fichaje fichar(Long empleadoId, Tipo tipo) {
        Empleado empleado = buscarEmpleado(empleadoId);

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de fichaje es obligatorio");
        }

        Optional<Fichaje> ultimo = fichajeRepository
                .findFirstByEmpleadoIdOrderByMomentoDescIdDesc(empleadoId);

        if (ultimo.isEmpty() && tipo != Tipo.ENTRADA) {
            throw new FichajeDuplicadoException(
                    "El primer fichaje debe ser ENTRADA"
            );
        }

        if (ultimo.isPresent() && ultimo.get().getTipo() == tipo) {
            throw new FichajeDuplicadoException(
                    "No se pueden registrar dos fichajes " + tipo + " seguidos"
            );
        }

        return guardar(empleado, tipo);
    }

    @Transactional(readOnly = true)
    public List<Fichaje> getFichajesDeEmpleado(Long empleadoId) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new EmpleadoNoEncontradoException(empleadoId);
        }

        return fichajeRepository
                .findByEmpleadoIdOrderByMomentoDescIdDesc(empleadoId);
    }

    private Empleado buscarEmpleado(Long empleadoId) {
        return empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(empleadoId));
    }

    private Fichaje guardar(Empleado empleado, Tipo tipo) {
        Fichaje fichaje = Fichaje.builder()
                .empleado(empleado)
                .tipo(tipo)
                .momento(LocalDateTime.now())
                .build();

        return fichajeRepository.save(fichaje);
    }
}
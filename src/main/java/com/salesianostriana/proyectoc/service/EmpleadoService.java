package com.salesianostriana.proyectoc.service;


import com.salesianostriana.proyectoc.dto.EditEmpleadoDto;
import com.salesianostriana.proyectoc.error.DepartamentoNoEncontradoException;
import com.salesianostriana.proyectoc.error.EmpleadoNoEncontradoException;
import com.salesianostriana.proyectoc.error.PresupuestoInsuficienteException;
import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.model.Empleado;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import com.salesianostriana.proyectoc.repository.EmpleadoRepository;
import com.salesianostriana.proyectoc.repository.FichajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final FichajeRepository fichajeRepository;

    @Transactional(readOnly = true)
    public List<Empleado> getAllEmpleados() {
        return  empleadoRepository.findAll();
    }

    public Empleado getEmpleadoById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(()-> new EmpleadoNoEncontradoException(id));
    }

    public Empleado addEmpleado(EditEmpleadoDto dto) {
        Empleado empleado = Empleado.builder()
                .nombreCompleto(dto.nombreCompleto())
                .cargo(dto.cargo())
                .salario(dto.salario())
                .build();

        if (dto.departamentoId() != null) {
            Departamento departamento = departamentoRepository
                    .findById(dto.departamentoId())
                    .orElseThrow(() ->
                            new DepartamentoNoEncontradoException(dto.departamentoId()));

            validarPresupuesto(departamento, empleado.getSalario(), null);
            empleado.setDepartamento(departamento);
        }

        return empleadoRepository.save(empleado);
    }

    private void validarPresupuesto(
            Departamento departamento,
            BigDecimal nuevoSalario,
            Long empleadoId
    ) {
        if (departamento == null) {
            return;
        }

        BigDecimal salariosActuales = empleadoRepository
                .findByDepartamentoId(departamento.getId())
                .stream()
                .filter(e -> empleadoId == null || !empleadoId.equals(e.getId()))
                .map(Empleado::getSalario)
                .filter(salario -> salario != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = salariosActuales.add(nuevoSalario);

        if (total.compareTo(departamento.getPresupuesto()) > 0) {
            throw new PresupuestoInsuficienteException(
                    "La suma de los salarios supera el presupuesto del departamento"
            );
        }
    }

    public Empleado updateEmpleado(Long id, EditEmpleadoDto dto) {
        Empleado empleado = getEmpleadoById(id);

        empleado.setNombreCompleto(dto.nombreCompleto());
        empleado.setCargo(dto.cargo());
        empleado.setSalario(dto.salario());
        // Si el DTO trae departamentoId, se asigna ese departamento.
        // Si es null, se conserva el departamento actual.
        if (dto.departamentoId() != null) {
            Departamento departamento = departamentoRepository
                    .findById(dto.departamentoId())
                    .orElseThrow(() ->
                            new DepartamentoNoEncontradoException(dto.departamentoId()));

            validarPresupuesto(departamento, dto.salario(), empleado.getId());
            empleado.setDepartamento(departamento);
        } else if (empleado.getDepartamento() != null) {
            validarPresupuesto(
                    empleado.getDepartamento(),
                    dto.salario(),
                    empleado.getId()
            );
        }

        return empleadoRepository.save(empleado);
    }

    public Empleado asignarDepartamento(Long empleadoId, Long departamentoId) {
        Empleado empleado = getEmpleadoById(empleadoId);

        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() ->
                        new DepartamentoNoEncontradoException(departamentoId));

        validarPresupuesto(
                departamento,
                empleado.getSalario(),
                empleado.getId()
        );

        empleado.setDepartamento(departamento);
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public void deleteEmpleado(Long id) {
        Empleado empleado = getEmpleadoById(id);
        fichajeRepository.deleteByEmpleadoId(id);
        empleadoRepository.delete(empleado);
    }


}

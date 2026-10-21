package com.salesianostriana.proyectoc.service;


import com.salesianostriana.proyectoc.dto.EditEmpleadoDto;
import com.salesianostriana.proyectoc.model.Empleado;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import com.salesianostriana.proyectoc.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public List<Empleado> getAllEmpleados() {
        List<Empleado> result = empleadoRepository.findAll();
        if (result.isEmpty()) {
            throw new RuntimeException();
        }
        return result;
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

        return empleadoRepository.save(empleado);
    }

    public Empleado updateEmpleado(Long id, EditEmpleadoDto editEmpleadoDto) {

        return empleadoRepository.findById(id)
                .map(e -> {
                        e.setNombreCompleto(editEmpleadoDto.nombreCompleto());
                        e.setCargo(editEmpleadoDto.cargo());
                        e.setSalario(editEmpleadoDto.salario());
                        return empleadoRepository.save(e);
                })
                .orElseThrow(() -> new RuntimeException(""));
    }

    public Empleado asignarDepartamento(Long empleadoId, Long departamentoId) {
        Empleado empleado = getEmpleadoById(empleadoId);

        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() ->
                        new DepartamentoNoEncontradoException(departamentoId));

        empleado.setDepartamento(departamento);
        return empleadoRepository.save(empleado);
    }

    public void deleteEmpleado (Long id) {
        empleadoRepository.deleteById(id);
    }


}

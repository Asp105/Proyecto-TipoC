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
                .orElseThrow(()-> new RuntimeException(""));
    }

    public Empleado addEmpleado(EditEmpleadoDto editEmpleadoDto) {
        validateEmpleado(editEmpleadoDto);
        return empleadoRepository.save(editEmpleadoDto.to());
    }

    public Empleado updateEmpleado(Long id, EditEmpleadoDto editEmpleadoDto) {

        return empleadoRepository.findById(id)
                .map(e -> {
                        validateEmpleado(editEmpleadoDto);
                        e.setNombreCompleto(editEmpleadoDto.nombreCompleto());
                        e.setCargo(editEmpleadoDto.cargo());
                        e.setSalario(editEmpleadoDto.salario());
                        return empleadoRepository.save(e);
                })
                .orElseThrow(() -> new RuntimeException(""));
    }

    public void deleteEmpleado(Empleado empleado) {
        empleadoRepository.delete(empleado);
    }

    public void deleteEmpleado (Long id) {
        empleadoRepository.deleteById(id);
    }


    private void validateEmpleado(EditEmpleadoDto editEmpleadoDto) {
        /*if (!StringUtils.hasText(editProductDto.name())
                || !StringUtils.hasText(editProductDto.details())) {
            throw new InvalidProductException("Product name and details are required");
        }
        if (editProductDto.price() < 0) {
            throw new InvalidProductException("Price is required");
        }*/
    }
}

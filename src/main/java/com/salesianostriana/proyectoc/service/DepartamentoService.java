package com.salesianostriana.proyectoc.service;

import com.salesianostriana.proyectoc.dto.EditDepartamentoDto;
import com.salesianostriana.proyectoc.error.DepartamentoConEmpleadosException;
import com.salesianostriana.proyectoc.error.DepartamentoNoEncontradoException;
import com.salesianostriana.proyectoc.error.DepartamentoNombreDuplicadoException;
import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import com.salesianostriana.proyectoc.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));
    }

    public Departamento addDepartamento(EditDepartamentoDto dto) {

        if (departamentoRepository.existsByName(dto.name())) {
            throw new DepartamentoNombreDuplicadoException(dto.name());
        }

        return departamentoRepository.save(
                Departamento.builder()
                        .name(dto.name())
                        .presupuesto(dto.presupuesto())
                        .build()
        );
    }

    public Departamento updateDepartamento(Long id, EditDepartamentoDto dto) {
        Departamento departamento = getDepartamentoById(id);

        boolean nombreUsado =
                departamentoRepository.existsByName(dto.name())
                        && !departamento.getName().equalsIgnoreCase(dto.name());

        if (nombreUsado) {
            throw new DepartamentoNombreDuplicadoException(dto.name());
        }

        departamento.setName(dto.name());
        departamento.setPresupuesto(dto.presupuesto());

        return departamentoRepository.save(departamento);
    }

    public void deleteDepartamento(Long id) {
        Departamento departamento = getDepartamentoById(id);

        if (empleadoRepository.existsByDepartamentoId(id)) {
            throw new DepartamentoConEmpleadosException(id);
        }

        departamentoRepository.delete(departamento);
    }


}

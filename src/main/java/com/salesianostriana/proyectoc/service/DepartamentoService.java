package com.salesianostriana.proyectoc.service;

import com.salesianostriana.proyectoc.dto.EditDepartamentoDto;
import com.salesianostriana.proyectoc.error.DepartamentoNoEncontradoException;
import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    @Transactional(readOnly = true)
    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));
    }

    public Departamento addDepartamento(EditDepartamentoDto dto) {
        return departamentoRepository.save(
                Departamento.builder()
                        .name(dto.name())
                        .presupuesto(dto.presupuesto())
                        .build()
        );
    }

    public Departamento updateDepartamento (Long id, EditDepartamentoDto editDepartamentoDto) {

        return departamentoRepository.findById(id)
                .map( d -> {
                        d.setName(editDepartamentoDto.name());
                        d.setPresupuesto(editDepartamentoDto.presupuesto());
                        return departamentoRepository.save(d);
                })
                .orElseThrow(() -> new DepartamentoNoEncontradoException(id));


    }

    public void deleteDepartamento(Long id) {
        Departamento departamento = getDepartamentoById(id);
        departamentoRepository.deleteById(id);
    }


}

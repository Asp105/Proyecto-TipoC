package com.salesianostriana.proyectoc.service;

import com.salesianostriana.proyectoc.dto.EditDepartamentoDto;
import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllProducts() {
        List<Departamento> result = departamentoRepository.findAll();
        if (result.isEmpty()) {
            throw new RuntimeException("Departamento no encontrado");
        }
        return result;
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
                .orElseThrow(() -> new RuntimeException(""));


    }

    public void deleteDepartamento(Long id) {
        departamentoRepository.deleteById(id);
    }


}

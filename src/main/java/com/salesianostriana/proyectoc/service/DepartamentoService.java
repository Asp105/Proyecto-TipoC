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
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
    }

    public Departamento addDe(EditDepartamentoDto editDepartamentoDto) {
        validateDepartamento(editDepartamentoDto);
        return departamentoRepository.save(editDepartamentoDto.to());
    }

    public Departamento updateDepartamento (Long id, EditDepartamentoDto editDepartamentoDto) {

        return departamentoRepository.findById(id)
                .map( d -> {
                        validateDepartamento(editDepartamentoDto);
                        d.setName(editDepartamentoDto.name());
                        d.setPresupuesto(editDepartamentoDto.presupuesto());
                        return departamentoRepository.save(d);
                })
                .orElseThrow(() -> new RuntimeException(""));


    }

    public void deleteDepartamento(Departamento departamento) {
        departamentoRepository.delete(departamento);
    }

    public void deleteDepartamento(Long id) {
        departamentoRepository.deleteById(id);
    }


    private void validateDepartamento(EditDepartamentoDto editDepartamentoDto) {
        /*if (!StringUtils.hasText(editProductDto.name())
                || !StringUtils.hasText(editProductDto.details())) {
            throw new InvalidProductException("Product name and details are required");
        }
        if (editProductDto.price() < 0) {
            throw new InvalidProductException("Price is required");
        }*/
    }

}

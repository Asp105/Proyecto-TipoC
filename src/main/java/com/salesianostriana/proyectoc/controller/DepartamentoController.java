package com.salesianostriana.proyectoc.controller;


import com.salesianostriana.proyectoc.dto.DepartamentoResponseDto;
import com.salesianostriana.proyectoc.dto.EditDepartamentoDto;
import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import com.salesianostriana.proyectoc.service.DepartamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departamento")
public class DepartamentoController {

        private final DepartamentoRepository departamentoRepository;
        private final DepartamentoService departamentoService;

        @PostMapping
        public ResponseEntity<DepartamentoResponseDto> addDepartamento(
                @Valid @RequestBody EditDepartamentoDto departamento) {

            return ResponseEntity.status(201)
                    .body(DepartamentoResponseDto.from(departamentoService.addDepartamento(departamento)));
        }

        @GetMapping
        public ResponseEntity<List<DepartamentoResponseDto>> getAllDepartamentos() {

            List<Departamento> result = departamentoService.getAllDepartamentos();
            return ResponseEntity.ok(
                    result
                            .stream()
                            .map(DepartamentoResponseDto::from)
                            .toList());
        }

        @GetMapping("/{id")
        public ResponseEntity<DepartamentoResponseDto> getDepartamentoById(@PathVariable Long id) {

            return ResponseEntity.ok(DepartamentoResponseDto.from(departamentoService.getDepartamentoById(id)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<DepartamentoResponseDto> updateDepartamento(
                @PathVariable Long id, @RequestBody EditDepartamentoDto departamento){

            return ResponseEntity.ok(
                    DepartamentoResponseDto.from(departamentoService.updateDepartamento(id, departamento)));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
            departamentoService.deleteDepartamento(id);
            return ResponseEntity.noContent().build();
        }
}

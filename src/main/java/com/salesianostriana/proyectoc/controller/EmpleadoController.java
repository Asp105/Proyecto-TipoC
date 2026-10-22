package com.salesianostriana.proyectoc.controller;


import com.salesianostriana.proyectoc.dto.EditEmpleadoDto;
import com.salesianostriana.proyectoc.dto.EmpleadoResponseDto;
import com.salesianostriana.proyectoc.model.Empleado;
import com.salesianostriana.proyectoc.repository.EmpleadoRepository;
import com.salesianostriana.proyectoc.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/empleado")
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoResponseDto> addEmpleado(
            @Valid @RequestBody EditEmpleadoDto empleado){

        return ResponseEntity.status(201)
                .body(EmpleadoResponseDto.from(empleadoService.addEmpleado(empleado)));

    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDto>> getAllEmpleados() {

        List<Empleado> result = empleadoService.getAllEmpleados();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(EmpleadoResponseDto::from)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> getEmpleadoById(@PathVariable Long id) {

        return ResponseEntity.ok(EmpleadoResponseDto.from(empleadoService.getEmpleadoById(id)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> updateEmpleado(
            @PathVariable Long id, @RequestBody EditEmpleadoDto empleado) {

        return ResponseEntity.ok(
                EmpleadoResponseDto.from(empleadoService.updateEmpleado(id, empleado)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}

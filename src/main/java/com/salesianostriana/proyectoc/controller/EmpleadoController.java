package com.salesianostriana.proyectoc.controller;

import com.salesianostriana.proyectoc.dto.EditEmpleadoDto;
import com.salesianostriana.proyectoc.dto.EmpleadoResponseDto;
import com.salesianostriana.proyectoc.model.Empleado;
import com.salesianostriana.proyectoc.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados")
@Validated
@Tag(name = "Empleado Controller", description = "Gestión de empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Operation(summary = "Crear empleado")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Empleado creado",
                    content = @Content(
                            schema = @Schema(implementation = EmpleadoResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombreCompleto": "Ana García",
                                      "cargo": "Desarrolladora",
                                      "salario": 2800.00,
                                      "departamentoId": null,
                                      "departamentoNombre": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping
    public ResponseEntity<EmpleadoResponseDto> addEmpleado(
            @Valid @RequestBody EditEmpleadoDto dto
    ) {
        Empleado empleado = empleadoService.addEmpleado(dto);

        return ResponseEntity.status(201)
                .body(EmpleadoResponseDto.from(empleado));
    }

    @Operation(summary = "Obtener todos los empleados")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de empleados",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = EmpleadoResponseDto.class
                                    )
                            ),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 1,
                                        "nombreCompleto": "Ana García",
                                        "cargo": "Desarrolladora",
                                        "salario": 2800.00,
                                        "departamentoId": 2,
                                        "departamentoNombre": "Desarrollo"
                                      }
                                    ]
                                    """)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDto>> getAllEmpleados() {
        List<EmpleadoResponseDto> empleados = empleadoService
                .getAllEmpleados()
                .stream()
                .map(EmpleadoResponseDto::from)
                .toList();

        return ResponseEntity.ok(empleados);
    }

    @Operation(summary = "Obtener un empleado por su identificador")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Empleado encontrado",
                    content = @Content(
                            schema = @Schema(implementation = EmpleadoResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un empleado con ese identificador",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> getEmpleadoById(
            @PathVariable @Min(1) Long id
    ) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.from(empleadoService.getEmpleadoById(id))
        );
    }

    @Operation(summary = "Actualizar un empleado")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Empleado actualizado",
                    content = @Content(
                            schema = @Schema(implementation = EmpleadoResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un empleado con ese identificador",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> updateEmpleado(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody EditEmpleadoDto dto
    ) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.from(
                        empleadoService.updateEmpleado(id, dto)
                )
        );
    }

    @Operation(summary = "Asignar un empleado a un departamento")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Empleado asignado al departamento",
                    content = @Content(
                            schema = @Schema(implementation = EmpleadoResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe el empleado o el departamento indicado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    public ResponseEntity<EmpleadoResponseDto> asignarDepartamento(
            @PathVariable @Min(1) Long empleadoId,
            @PathVariable @Min(1) Long deptoId
    ) {
        return ResponseEntity.ok(
                EmpleadoResponseDto.from(
                        empleadoService.asignarDepartamento(empleadoId, deptoId)
                )
        );
    }

    @Operation(summary = "Eliminar un empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un empleado con ese identificador",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(
            @PathVariable @Min(1) Long id
    ) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
package com.salesianostriana.proyectoc.controller;

import com.salesianostriana.proyectoc.dto.DepartamentoResponseDto;
import com.salesianostriana.proyectoc.dto.EditDepartamentoDto;
import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.service.DepartamentoService;
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
@RequestMapping("/api/v1/departamentos")
@Validated
@Tag(name = "Departamento Controller", description = "Gestión de departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @Operation(summary = "Crear departamento")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Departamento creado",
                    content = @Content(
                            schema = @Schema(implementation = DepartamentoResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Desarrollo",
                                      "presupuesto": 50000.00
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe otro departamento con ese nombre",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                        {
                          "title": "Nombre de departamento duplicado",
                          "status": 409,
                          "detail": "Ya existe un departamento con el nombre: Desarrollo"
                        }
                        """)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DepartamentoResponseDto> addDepartamento(
            @Valid @RequestBody EditDepartamentoDto dto
    ) {
        Departamento departamento = departamentoService.addDepartamento(dto);

        return ResponseEntity.status(201)
                .body(DepartamentoResponseDto.from(departamento));
    }

    @Operation(summary = "Obtener todos los departamentos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de departamentos",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = DepartamentoResponseDto.class
                                    )
                            ),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 1,
                                        "nombre": "Desarrollo",
                                        "presupuesto": 50000.00
                                      },
                                      {
                                        "id": 2,
                                        "nombre": "Administración",
                                        "presupuesto": 30000.00
                                      }
                                    ]
                                    """)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDto>> getAllDepartamentos() {
        List<DepartamentoResponseDto> departamentos = departamentoService
                .getAllDepartamentos()
                .stream()
                .map(DepartamentoResponseDto::from)
                .toList();

        return ResponseEntity.ok(departamentos);
    }

    @Operation(summary = "Obtener un departamento por su identificador")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departamento encontrado",
                    content = @Content(
                            schema = @Schema(implementation = DepartamentoResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Desarrollo",
                                      "presupuesto": 50000.00
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un departamento con ese identificador",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> getDepartamentoById(
            @PathVariable @Min(1) Long id
    ) {
        return ResponseEntity.ok(
                DepartamentoResponseDto.from(
                        departamentoService.getDepartamentoById(id)
                )
        );
    }

    @Operation(summary = "Actualizar un departamento")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departamento actualizado",
                    content = @Content(
                            schema = @Schema(implementation = DepartamentoResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un departamento con ese identificador",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe otro departamento con ese nombre",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                        {
                          "title": "Nombre de departamento duplicado",
                          "status": 409,
                          "detail": "Ya existe un departamento con el nombre: Desarrollo"
                        }
                        """)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> updateDepartamento(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody EditDepartamentoDto dto
    ) {
        return ResponseEntity.ok(
                DepartamentoResponseDto.from(
                        departamentoService.updateDepartamento(id, dto)
                )
        );
    }

    @Operation(summary = "Eliminar un departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Departamento eliminado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un departamento con ese identificador",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El departamento tiene empleados asignados",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                        {
                          "title": "Departamento con empleados",
                          "status": 409,
                          "detail": "No se puede eliminar el departamento 1 porque tiene empleados asignados"
                        }
                        """)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(
            @PathVariable @Min(1) Long id
    ) {
        departamentoService.deleteDepartamento(id);
        return ResponseEntity.noContent().build();
    }
}
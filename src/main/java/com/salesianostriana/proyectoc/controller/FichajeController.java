package com.salesianostriana.proyectoc.controller;

import com.salesianostriana.proyectoc.dto.FichajeRequestDto;
import com.salesianostriana.proyectoc.dto.FichajeResponseDto;
import com.salesianostriana.proyectoc.model.Fichaje;
import com.salesianostriana.proyectoc.service.FichajesService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados/{empleadoId}")
@Validated
@Tag(name = "Fichaje Controller", description = "Registro horario de empleados")
public class FichajeController {

    private final FichajesService fichajesService;

    @Operation(
            summary = "Registrar un fichaje",
            description = "Sin cuerpo, crea automáticamente una entrada o salida alternando " +
                    "el tipo. Si se envía un tipo, comprueba que no repita el último fichaje."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Fichaje registrado",
                    content = @Content(
                            schema = @Schema(implementation = FichajeResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 10,
                                      "momento": "2026-10-22T08:30:00",
                                      "tipo": "ENTRADA"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Tipo de fichaje inválido",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe el empleado indicado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El tipo solicitado repite el último fichaje",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping("/fichar")
    public ResponseEntity<FichajeResponseDto> fichar(
            @PathVariable @Min(1) Long empleadoId,
            @Valid @RequestBody(required = false) FichajeRequestDto request
    ) {
        Fichaje fichaje = request == null
                ? fichajesService.fichar(empleadoId)
                : fichajesService.fichar(empleadoId, request.tipo());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FichajeResponseDto.from(fichaje));
    }

    @Operation(summary = "Obtener el historial de fichajes de un empleado")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Historial de fichajes, del más reciente al más antiguo",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = FichajeResponseDto.class
                                    )
                            ),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 11,
                                        "momento": "2026-10-22T17:00:00",
                                        "tipo": "SALIDA"
                                      },
                                      {
                                        "id": 10,
                                        "momento": "2026-10-22T08:30:00",
                                        "tipo": "ENTRADA"
                                      }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe el empleado indicado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/fichajes")
    public ResponseEntity<List<FichajeResponseDto>> getFichajes(
            @PathVariable @Min(1) Long empleadoId
    ) {
        List<FichajeResponseDto> fichajes = fichajesService
                .getFichajesDeEmpleado(empleadoId)
                .stream()
                .map(FichajeResponseDto::from)
                .toList();

        return ResponseEntity.ok(fichajes);
    }
}
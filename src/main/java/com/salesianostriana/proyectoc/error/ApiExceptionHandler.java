package com.salesianostriana.proyectoc.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            EmpleadoNoEncontradoException.class,
            DepartamentoNoEncontradoException.class
    })
    public ProblemDetail notFound(RuntimeException ex) {
        ProblemDetail problem =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Recurso no encontrado");
        return problem;
    }

    @ExceptionHandler(FichajeDuplicadoException.class)
    public ProblemDetail fichajeDuplicado(FichajeDuplicadoException ex) {
        ProblemDetail problem =
                ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Fichaje duplicado");
        return problem;
    }

    @ExceptionHandler(PresupuestoInsuficienteException.class)
    public ProblemDetail presupuestoInsuficiente(
            PresupuestoInsuficienteException ex
    ) {
        ProblemDetail problem =
                ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Presupuesto insuficiente");
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Error de validación");
        problem.setDetail("Hay campos con valores no válidos.");
        problem.setProperty("errores", ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList());
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail constraintViolation(ConstraintViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Error de validación");
        problem.setDetail("Uno o más parámetros de la petición no son válidos.");
        problem.setProperty(
                "errores",
                ex.getConstraintViolations()
                        .stream()
                        .map(error -> error.getPropertyPath() + ": "
                                + error.getMessage())
                        .toList()
        );
        return problem;
    }
}
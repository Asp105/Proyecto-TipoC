package com.salesianostriana.proyectoc.error;

public class EmpleadoNoEncontradoException extends RuntimeException {
    public EmpleadoNoEncontradoException(Long id) {
        super("No se ha encontrado el empleado con id " + id);
    }
}
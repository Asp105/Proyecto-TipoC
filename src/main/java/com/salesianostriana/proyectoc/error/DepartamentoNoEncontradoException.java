package com.salesianostriana.proyectoc.error;

public class DepartamentoNoEncontradoException extends RuntimeException {
    public DepartamentoNoEncontradoException(Long id) {
        super("No se ha encontrado el empleado con id " + id);
    }
}

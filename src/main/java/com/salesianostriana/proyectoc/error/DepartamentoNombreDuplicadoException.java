package com.salesianostriana.proyectoc.error;

public class DepartamentoNombreDuplicadoException extends RuntimeException {

    public DepartamentoNombreDuplicadoException(String nombre) {
        super("Ya existe un departamento con el nombre: " + nombre);
    }
}
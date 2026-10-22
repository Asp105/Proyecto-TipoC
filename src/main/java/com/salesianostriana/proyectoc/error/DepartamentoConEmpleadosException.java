package com.salesianostriana.proyectoc.error;

public class DepartamentoConEmpleadosException extends RuntimeException {

    public DepartamentoConEmpleadosException(Long id) {
        super("No se puede eliminar el departamento " + id
                + " porque tiene empleados asignados");
    }
}
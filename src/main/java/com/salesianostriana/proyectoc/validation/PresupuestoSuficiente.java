package com.salesianostriana.proyectoc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PresupuestoSuficienteValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PresupuestoSuficiente {
    String message() default
            "La suma de los salarios supera el presupuesto del departamento";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
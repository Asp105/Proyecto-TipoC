package com.salesianostriana.proyectoc.validation;

import com.salesianostriana.proyectoc.dto.EditEmpleadoDto;
import com.salesianostriana.proyectoc.model.Departamento;
import com.salesianostriana.proyectoc.model.Empleado;
import com.salesianostriana.proyectoc.repository.DepartamentoRepository;
import com.salesianostriana.proyectoc.repository.EmpleadoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PresupuestoSuficienteValidator
        implements ConstraintValidator<PresupuestoSuficiente, EditEmpleadoDto> {

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    public boolean isValid(EditEmpleadoDto dto, ConstraintValidatorContext context) {
        // El resto de anotaciones se encarga de los valores nulos.
        if (dto == null || dto.salario() == null) {
            return true;
        }

        // Un empleado todavía no asignado no consume presupuesto departamental.
        if (dto.departamentoId() == null) {
            return true;
        }

        Departamento departamento = departamentoRepository
                .findById(dto.departamentoId())
                .orElse(null);

        // El servicio/controlador gestionará el departamento inexistente.
        if (departamento == null || departamento.getPresupuesto() == null) {
            return true;
        }

        BigDecimal salariosActuales = empleadoRepository
                .findByDepartamentoId(dto.departamentoId())
                .stream()
                // Al actualizar, no contamos dos veces el salario del propio empleado.
                .filter(e -> dto.empleadoId() == null
                        || !dto.empleadoId().equals(e.getId()))
                .map(Empleado::getSalario)
                .filter(salario -> salario != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalNuevo = salariosActuales.add(dto.salario());

        return totalNuevo.compareTo(departamento.getPresupuesto()) <= 0;
    }
}
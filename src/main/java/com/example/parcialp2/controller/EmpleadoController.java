package com.example.parcialp2.controller;

import com.example.parcialp2.model.Departamento;
import com.example.parcialp2.model.Empleado;
import com.example.parcialp2.repository.DepartamentoRepository;
import com.example.parcialp2.repository.EmpleadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Empleados", description = "Operaciones relacionadas con empleados")
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoController(EmpleadoRepository empleadoRepository, DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    // ✅ Crear un nuevo empleado (ahora con departamento)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Empleado empleado) {
        if (empleado.getDepartamento() == null || empleado.getDepartamento().getId() == null) {
            return ResponseEntity.badRequest().body("El departamento es obligatorio.");
        }

        Optional<Departamento> departamento = departamentoRepository.findById(empleado.getDepartamento().getId());
        if (departamento.isEmpty()) {
            return ResponseEntity.badRequest().body("El departamento no existe.");
        }

        empleado.setDepartamento(departamento.get());
        Empleado nuevoEmpleado = empleadoRepository.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }

    // ✅ Actualizar un empleado (incluye departamento)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(empleadoActualizado.getNombre());
                    empleado.setCargo(empleadoActualizado.getCargo());
                    empleado.setSalario(empleadoActualizado.getSalario());
                    empleado.setFechaContratacion(empleadoActualizado.getFechaContratacion());

                    // Validar el departamento antes de actualizarlo
                    if (empleadoActualizado.getDepartamento() != null && empleadoActualizado.getDepartamento().getId() != null) {
                        Optional<Departamento> departamento = departamentoRepository.findById(empleadoActualizado.getDepartamento().getId());
                        departamento.ifPresent(empleado::setDepartamento);
                    }

                    empleadoRepository.save(empleado);
                    return ResponseEntity.ok(empleado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

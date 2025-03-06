package com.example.parcialp2.controller;

import com.example.parcialp2.model.Departamento;
import com.example.parcialp2.repository.DepartamentoRepository;
import com.example.parcialp2.service.DepartamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departamentos")
@Tag(name = "Departamento", description = "Operaciones relacionadas con departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    // ✅ Obtener todos los departamentos
    @GetMapping
    public List<Departamento> obtenerTodos() {
        return departamentoService.obtenerTodos();
    }

    // ✅ Obtener un departamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> obtenerPorId(@PathVariable Long id) {
        return departamentoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Crear un nuevo departamento
    @PostMapping
    public ResponseEntity<Departamento> crear(@RequestBody Departamento departamento) {
        Departamento nuevoDepartamento = departamentoService.crear(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDepartamento);
    }

    // ✅ Actualizar un departamento
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Departamento departamentoActualizado) {
        return departamentoService.actualizar(id, departamentoActualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Eliminar un departamento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (departamentoService.eliminar(id)) {
            return ResponseEntity.ok().body("Departamento eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departamento no encontrado.");
        }
    }
}


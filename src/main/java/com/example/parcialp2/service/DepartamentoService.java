package com.example.parcialp2.service;

import com.example.parcialp2.model.Departamento;
import com.example.parcialp2.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    // Obtener todos los departamentos
    public List<Departamento> obtenerTodos() {
        return departamentoRepository.findAll();
    }

    // Obtener un departamento por ID
    public Optional<Departamento> obtenerPorId(Long id) {
        return departamentoRepository.findById(id);
    }

    // Crear un nuevo departamento
    public Departamento crear(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    // Actualizar un departamento
    public Optional<Departamento> actualizar(Long id, Departamento departamentoActualizado) {
        return departamentoRepository.findById(id).map(departamento -> {
            departamento.setNombre(departamentoActualizado.getNombre());
            return departamentoRepository.save(departamento);
        });
    }

    // Eliminar un departamento
    public boolean eliminar(Long id) {
        if (departamentoRepository.existsById(id)) {
            departamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

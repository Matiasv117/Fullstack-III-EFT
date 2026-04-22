package com.saludrednorte.ms_listas_espera.controller;

import com.saludrednorte.ms_listas_espera.entity.Paciente;
import com.saludrednorte.ms_listas_espera.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public Paciente registrarPaciente(@RequestBody Paciente paciente) {
        return pacienteService.registrarPaciente(paciente);
    }

    @GetMapping
    public List<Paciente> obtenerTodosPacientes() {
        return pacienteService.obtenerTodosPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable Long id) {
        return pacienteService.obtenerPacientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Paciente actualizarPaciente(@RequestBody Paciente paciente) {
        return pacienteService.actualizarPaciente(paciente);
    }

    @DeleteMapping("/{id}")
    public void eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
    }
}

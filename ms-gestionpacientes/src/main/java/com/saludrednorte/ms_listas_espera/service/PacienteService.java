package com.saludrednorte.ms_listas_espera.service;

import com.saludrednorte.ms_listas_espera.entity.Paciente;
import com.saludrednorte.ms_listas_espera.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente registrarPaciente(Paciente paciente) {
        if (paciente.getDni() != null && pacienteRepository.existsByDniIgnoreCase(paciente.getDni())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un paciente con el DNI indicado");
        }
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> obtenerTodosPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPacientePorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente actualizarPaciente(Paciente paciente) {
        if (paciente.getId() == null || !pacienteRepository.existsById(paciente.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        return pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}

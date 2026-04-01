package com.saludrednorte.ms_listas_espera.service;

import com.saludrednorte.ms_listas_espera.entity.Paciente;
import com.saludrednorte.ms_listas_espera.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> obtenerTodosPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPacientePorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente actualizarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }
}

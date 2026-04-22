package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.entity.Cita;
import com.saludrednorte.ms_optimizacion.entity.EstadoCita;
import com.saludrednorte.ms_optimizacion.repository.CitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Cita crearCita(Cita cita) {
        if (cita.getMedico() == null || cita.getFechaHora() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cita requiere medico y fecha/hora");
        }
        if (citaRepository.existsByMedicoAndFechaHoraAndEstadoNot(cita.getMedico(), cita.getFechaHora(), EstadoCita.CANCELADA)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El medico ya tiene una cita en ese horario");
        }
        cita.setEstado(EstadoCita.CONFIRMADA);
        return citaRepository.save(cita);
    }

    public List<Cita> obtenerTodasCitas() {
        return citaRepository.findAll();
    }

    public List<Cita> obtenerCitasPorEstado(EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }

    public Optional<Cita> obtenerCitaPorId(Long id) {
        return citaRepository.findById(id);
    }

    public Cita actualizarCita(Cita cita) {
        if (cita.getId() == null || !citaRepository.existsById(cita.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada");
        }
        return citaRepository.save(cita);
    }

    public void cancelarCita(Long id) {
        Optional<Cita> optional = citaRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada");
        }

        Cita cita = optional.get();
        cita.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(cita);
    }

    public void eliminarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }
}

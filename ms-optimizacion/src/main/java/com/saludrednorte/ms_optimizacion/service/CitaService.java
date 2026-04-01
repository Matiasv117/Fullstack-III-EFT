package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.entity.Cita;
import com.saludrednorte.ms_optimizacion.entity.EstadoCita;
import com.saludrednorte.ms_optimizacion.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Cita crearCita(Cita cita) {
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
        return citaRepository.save(cita);
    }

    public void cancelarCita(Long id) {
        Optional<Cita> optional = citaRepository.findById(id);
        if (optional.isPresent()) {
            Cita cita = optional.get();
            cita.setEstado(EstadoCita.CANCELADA);
            citaRepository.save(cita);
        }
    }

    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }
}

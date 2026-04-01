package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.entity.Horario;
import com.saludrednorte.ms_optimizacion.entity.Medico;
import com.saludrednorte.ms_optimizacion.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    public Horario crearHorario(Horario horario) {
        return horarioRepository.save(horario);
    }

    public List<Horario> obtenerTodosHorarios() {
        return horarioRepository.findAll();
    }

    public List<Horario> obtenerHorariosDisponibles(Medico medico, LocalDate fecha) {
        return horarioRepository.findByMedicoAndFechaAndDisponible(medico, fecha, true);
    }

    public Optional<Horario> obtenerHorarioPorId(Long id) {
        return horarioRepository.findById(id);
    }

    public Horario actualizarHorario(Horario horario) {
        return horarioRepository.save(horario);
    }

    public void eliminarHorario(Long id) {
        horarioRepository.deleteById(id);
    }
}

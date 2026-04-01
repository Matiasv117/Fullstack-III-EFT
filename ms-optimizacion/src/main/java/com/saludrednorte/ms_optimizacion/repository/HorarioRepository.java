package com.saludrednorte.ms_optimizacion.repository;

import com.saludrednorte.ms_optimizacion.entity.Horario;
import com.saludrednorte.ms_optimizacion.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByMedicoAndFechaAndDisponible(Medico medico, LocalDate fecha, boolean disponible);
}

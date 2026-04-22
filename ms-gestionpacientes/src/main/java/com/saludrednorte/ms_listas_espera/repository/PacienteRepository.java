package com.saludrednorte.ms_listas_espera.repository;

import com.saludrednorte.ms_listas_espera.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	boolean existsByDniIgnoreCase(String dni);
}

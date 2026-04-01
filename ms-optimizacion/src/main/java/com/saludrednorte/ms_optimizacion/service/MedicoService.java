package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.entity.Medico;
import com.saludrednorte.ms_optimizacion.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico registrarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    public List<Medico> obtenerTodosMedicos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id);
    }

    public Medico actualizarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    public void eliminarMedico(Long id) {
        medicoRepository.deleteById(id);
    }
}

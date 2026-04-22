package com.saludrednorte.ms_optimizacion.controller;

import com.saludrednorte.ms_optimizacion.entity.Medico;
import com.saludrednorte.ms_optimizacion.service.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public Medico registrarMedico(@RequestBody Medico medico) {
        return medicoService.registrarMedico(medico);
    }

    @GetMapping
    public List<Medico> obtenerTodosMedicos() {
        return medicoService.obtenerTodosMedicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerMedicoPorId(@PathVariable Long id) {
        return medicoService.obtenerMedicoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Medico actualizarMedico(@RequestBody Medico medico) {
        return medicoService.actualizarMedico(medico);
    }

    @DeleteMapping("/{id}")
    public void eliminarMedico(@PathVariable Long id) {
        medicoService.eliminarMedico(id);
    }
}

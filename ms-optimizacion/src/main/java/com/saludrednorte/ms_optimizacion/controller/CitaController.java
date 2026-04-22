package com.saludrednorte.ms_optimizacion.controller;

import com.saludrednorte.ms_optimizacion.entity.Cita;
import com.saludrednorte.ms_optimizacion.entity.EstadoCita;
import com.saludrednorte.ms_optimizacion.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public Cita crearCita(@RequestBody Cita cita) {
        return citaService.crearCita(cita);
    }

    @GetMapping
    public List<Cita> obtenerTodasCitas() {
        return citaService.obtenerTodasCitas();
    }

    @GetMapping("/estado/{estado}")
    public List<Cita> obtenerCitasPorEstado(@PathVariable EstadoCita estado) {
        return citaService.obtenerCitasPorEstado(estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Long id) {
        return citaService.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Cita actualizarCita(@RequestBody Cita cita) {
        return citaService.actualizarCita(cita);
    }

    @DeleteMapping("/{id}")
    public void cancelarCita(@PathVariable Long id) {
        citaService.cancelarCita(id);
    }
}

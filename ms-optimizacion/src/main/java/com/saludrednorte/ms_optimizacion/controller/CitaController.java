package com.saludrednorte.ms_optimizacion.controller;

import com.saludrednorte.ms_optimizacion.entity.Cita;
import com.saludrednorte.ms_optimizacion.entity.EstadoCita;
import com.saludrednorte.ms_optimizacion.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Cita> obtenerCitaPorId(@PathVariable Long id) {
        return citaService.obtenerCitaPorId(id);
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

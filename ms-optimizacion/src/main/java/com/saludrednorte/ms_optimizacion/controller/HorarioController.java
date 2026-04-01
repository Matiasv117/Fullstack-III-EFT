package com.saludrednorte.ms_optimizacion.controller;

import com.saludrednorte.ms_optimizacion.entity.Horario;
import com.saludrednorte.ms_optimizacion.entity.Medico;
import com.saludrednorte.ms_optimizacion.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping
    public Horario crearHorario(@RequestBody Horario horario) {
        return horarioService.crearHorario(horario);
    }

    @GetMapping
    public List<Horario> obtenerTodosHorarios() {
        return horarioService.obtenerTodosHorarios();
    }

    @GetMapping("/disponibles")
    public List<Horario> obtenerHorariosDisponibles(@RequestParam Long medicoId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Medico medico = new Medico();
        medico.setId(medicoId);
        return horarioService.obtenerHorariosDisponibles(medico, fecha);
    }

    @GetMapping("/{id}")
    public Optional<Horario> obtenerHorarioPorId(@PathVariable Long id) {
        return horarioService.obtenerHorarioPorId(id);
    }

    @PutMapping
    public Horario actualizarHorario(@RequestBody Horario horario) {
        return horarioService.actualizarHorario(horario);
    }

    @DeleteMapping("/{id}")
    public void eliminarHorario(@PathVariable Long id) {
        horarioService.eliminarHorario(id);
    }
}

package com.saludrednorte.ms_optimizacion.controller;

import com.saludrednorte.ms_optimizacion.service.OptimizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/optimizacion")
public class OptimizacionController {

    @Autowired
    private OptimizacionService optimizacionService;

    @PostMapping("/cancelar/{citaId}")
    public void procesarCancelacion(@PathVariable Long citaId, @RequestParam(defaultValue = "fifo") String estrategia) {
        optimizacionService.procesarCancelacion(citaId, estrategia);
    }

    @GetMapping("/lista-espera")
    public String obtenerListaEspera() {
        return optimizacionService.obtenerListaEspera();
    }
}

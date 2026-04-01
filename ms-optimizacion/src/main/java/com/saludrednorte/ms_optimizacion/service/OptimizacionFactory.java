package com.saludrednorte.ms_optimizacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptimizacionFactory {

    @Autowired
    private EstrategiaPorGravedad estrategiaPorGravedad;

    @Autowired
    private EstrategiaFIFO estrategiaFIFO;

    public EstrategiaOptimizacion getEstrategia(String tipo) {
        switch (tipo.toLowerCase()) {
            case "gravedad":
                return estrategiaPorGravedad;
            case "fifo":
                return estrategiaFIFO;
            default:
                return estrategiaFIFO; // default
        }
    }
}

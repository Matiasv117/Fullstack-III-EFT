package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.entity.Cita;

public interface EstrategiaOptimizacion {
    void reasignarCita(Cita citaCancelada);
}

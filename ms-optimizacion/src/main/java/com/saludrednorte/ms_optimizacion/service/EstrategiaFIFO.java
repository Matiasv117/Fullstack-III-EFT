package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class EstrategiaFIFO implements EstrategiaOptimizacion {

    @Override
    public void reasignarCita(Cita citaCancelada) {
        // Lógica para reasignar basada en FIFO: primer paciente en lista de espera
        System.out.println("Reasignando cita cancelada usando estrategia FIFO");
    }
}

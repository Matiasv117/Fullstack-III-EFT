package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class EstrategiaPorGravedad implements EstrategiaOptimizacion {

    @Override
    public void reasignarCita(Cita citaCancelada) {
        // Lógica para reasignar basada en gravedad: obtener pacientes de lista de espera con gravedad alta primero
        // Usar llamada a ms-gestionpacientes para obtener lista de espera
        // Luego asignar al horario disponible más cercano
        System.out.println("Reasignando cita cancelada usando estrategia por gravedad");
    }
}

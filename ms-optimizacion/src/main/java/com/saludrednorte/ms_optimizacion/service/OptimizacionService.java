package com.saludrednorte.ms_optimizacion.service;

import com.saludrednorte.ms_optimizacion.client.ListaEsperaClient;
import com.saludrednorte.ms_optimizacion.client.NotificationClient;
import com.saludrednorte.ms_optimizacion.dto.ListaEsperaDTO;
import com.saludrednorte.ms_optimizacion.dto.NotificationRequestDTO;
import com.saludrednorte.ms_optimizacion.entity.Cita;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptimizacionService {

    private static final Logger logger = LoggerFactory.getLogger(OptimizacionService.class);

    @Autowired
    private OptimizacionFactory factory;

    @Autowired
    private CitaService citaService;

    @Autowired
    private ListaEsperaClient listaEsperaClient;

    @Autowired
    private NotificationClient notificationClient;

    public void procesarCancelacion(Long citaId, String estrategiaTipo) {
        citaService.cancelarCita(citaId);
        Cita citaCancelada = citaService.obtenerCitaPorId(citaId).orElse(null);
        if (citaCancelada != null) {
            EstrategiaOptimizacion estrategia = factory.getEstrategia(estrategiaTipo);
            estrategia.reasignarCita(citaCancelada);

            // Notificar reasignación de cita
            try {
                NotificationRequestDTO notif = new NotificationRequestDTO();
                notif.setPacienteId(citaCancelada.getPacienteId());
                notif.setTipo("CITA_REASIGNADA");
                notif.setMensaje("Cita reasignada para " + citaCancelada.getFechaHora());
                notificationClient.createNotification(notif);
                logger.info("Notificación de reasignación enviada para cita {}", citaCancelada.getId());
            } catch (Exception e) {
                logger.warn("Fallo al notificar reasignación de cita {} : {}", citaCancelada.getId(), e.getMessage());
            }
        }
    }

    @CircuitBreaker(name = "listaEsperaService", fallbackMethod = "fallbackListaEspera")
    public List<ListaEsperaDTO> obtenerListaEspera() {
        // Llamada a ms-gestionpacientes usando Feign
        return listaEsperaClient.getListaEspera();
    }

    public List<ListaEsperaDTO> fallbackListaEspera(Throwable t) {
        // Retornar lista vacía o datos locales
        return List.of();
    }
}

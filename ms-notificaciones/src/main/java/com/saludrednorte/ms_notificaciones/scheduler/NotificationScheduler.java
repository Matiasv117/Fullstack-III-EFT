package com.saludrednorte.ms_notificaciones.scheduler;

import com.saludrednorte.ms_notificaciones.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler para envíos automáticos de notificaciones
 * Ejecuta tareas programadas para procesar notificaciones pendientes
 * sin intervención manual
 */
@Component
public class NotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Envía todas las notificaciones pendientes cada 30 segundos
     */
    @Scheduled(fixedRate = 30000)
    public void enviarNotificacionesPendientes() {
        try {
            logger.debug("Iniciando tarea programada de envío de notificaciones");
            notificationService.sendPending();
            logger.debug("Tarea programada de envío completada");
        } catch (Exception e) {
            logger.error("Error en la tarea programada de envío de notificaciones", e);
        }
    }
}

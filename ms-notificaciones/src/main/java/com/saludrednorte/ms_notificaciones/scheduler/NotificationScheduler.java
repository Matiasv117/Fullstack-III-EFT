package com.saludrednorte.ms_notificaciones.scheduler;

import com.saludrednorte.ms_notificaciones.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Se ejecuta automáticamente en segundo plano
     * Útil para procesar notificaciones que lleguen durante la operación del sistema
     */
    @Scheduled(fixedRate = 30000) // 30 segundos
    public void enviarNotificacionesPendientes() {
        try {
            logger.debug("Iniciando tarea programada de envío de notificaciones");
            notificationService.sendPending();
            logger.debug("Tarea programada de envío completada");
        } catch (Exception e) {
            logger.error("Error en la tarea programada de envío de notificaciones", e);
        }
    }

    /**
     * Ejecuta un envío de mantenimiento cada hora
     * Reintenta enviar notificaciones que estén en estado REINTENTANDO
     */
    @Scheduled(cron = "0 0 * * * *") // Cada hora
    public void mantenimientoNotificaciones() {
        try {
            logger.info("Ejecutando tareas de mantenimiento de notificaciones");
            notificationService.sendPending();
            logger.info("Tareas de mantenimiento completadas");
        } catch (Exception e) {
            logger.error("Error en las tareas de mantenimiento de notificaciones", e);
        }
    }
}


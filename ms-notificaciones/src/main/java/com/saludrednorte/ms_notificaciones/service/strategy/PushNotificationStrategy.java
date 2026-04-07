package com.saludrednorte.ms_notificaciones.service.strategy;

import com.saludrednorte.ms_notificaciones.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación de NotificationStrategy para envío por Push Notification
 * Simula el envío de notificaciones push a la aplicación móvil/web
 */
@Component
public class PushNotificationStrategy implements NotificationStrategy {

    private static final Logger logger = LoggerFactory.getLogger(PushNotificationStrategy.class);

    @Override
    public void send(Notification notification) throws Exception {
        logger.info("Enviando notificación por PUSH - Paciente: {}, Tipo: {}",
                   notification.getPacienteId(), notification.getTipo());

        // Aquí iría la lógica real de envío push
        // Ejemplo: firebaseService.sendPush(pacienteDeviceId, notification.getMensaje());

        // Simulación
        Thread.sleep(20);
        logger.debug("Push notification enviada exitosamente a paciente {}", notification.getPacienteId());
    }

    @Override
    public String getChannelName() {
        return "PUSH";
    }
}


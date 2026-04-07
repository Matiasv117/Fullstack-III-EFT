package com.saludrednorte.ms_notificaciones.service.strategy;

import com.saludrednorte.ms_notificaciones.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación de NotificationStrategy para envío por SMS
 * Simula el envío de mensajes SMS a pacientes
 */
@Component
public class SMSNotificationStrategy implements NotificationStrategy {

    private static final Logger logger = LoggerFactory.getLogger(SMSNotificationStrategy.class);

    @Override
    public void send(Notification notification) throws Exception {
        logger.info("Enviando notificación por SMS - Paciente: {}, Tipo: {}",
                   notification.getPacienteId(), notification.getTipo());

        // Aquí iría la lógica real de envío de SMS
        // Ejemplo: smsService.sendSMS(pacienteTelefono, notification.getMensaje());

        // Simulación
        Thread.sleep(30);
        logger.debug("SMS enviado exitosamente a paciente {}", notification.getPacienteId());
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}


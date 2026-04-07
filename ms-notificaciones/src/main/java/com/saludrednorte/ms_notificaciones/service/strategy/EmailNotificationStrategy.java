package com.saludrednorte.ms_notificaciones.service.strategy;

import com.saludrednorte.ms_notificaciones.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación de NotificationStrategy para envío por Email
 * Simula el envío de emails a pacientes
 */
@Component
public class EmailNotificationStrategy implements NotificationStrategy {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationStrategy.class);

    @Override
    public void send(Notification notification) throws Exception {
        logger.info("Enviando notificación por EMAIL - Paciente: {}, Tipo: {}",
                   notification.getPacienteId(), notification.getTipo());

        // Aquí iría la lógica real de envío de email
        // Ejemplo: emailService.sendEmail(pacienteEmail, notification.getMensaje());

        // Simulación
        Thread.sleep(50);
        logger.debug("Email enviado exitosamente a paciente {}", notification.getPacienteId());
    }

    @Override
    public String getChannelName() {
        return "EMAIL";
    }
}


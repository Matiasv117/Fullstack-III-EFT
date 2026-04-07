package com.saludrednorte.ms_notificaciones.service;

import com.saludrednorte.ms_notificaciones.entity.Notification;
import com.saludrednorte.ms_notificaciones.entity.EstadoNotificacion;
import com.saludrednorte.ms_notificaciones.repository.NotificationRepository;
import com.saludrednorte.ms_notificaciones.service.factory.NotificationStrategyFactory;
import com.saludrednorte.ms_notificaciones.service.strategy.NotificationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private static final String CANAL_DEFECTO = "EMAIL";

    private final NotificationRepository repository;
    private final NotificationStrategyFactory strategyFactory;

    public NotificationService(NotificationRepository repository, NotificationStrategyFactory strategyFactory) {
        this.repository = repository;
        this.strategyFactory = strategyFactory;
    }

    public Notification create(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("La notificación no puede ser nula");
        }
        notification.setCreadoAt(notification.getCreadoAt() != null ? notification.getCreadoAt() : LocalDateTime.now());
        notification.setEstado(EstadoNotificacion.PENDIENTE);
        notification.setIntentosEnvio(notification.getIntentosEnvio() == null ? 0 : notification.getIntentosEnvio());
        return repository.save(notification);
    }

    public List<Notification> findPending() {
        return repository.findByEstado(EstadoNotificacion.PENDIENTE);
    }

    public List<Notification> findByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }

    public Optional<Notification> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void sendPending() {
        List<Notification> pendientes = findPending();
        for (Notification n : pendientes) {
            sendNotification(n, CANAL_DEFECTO);
        }
    }

    @Transactional
    public boolean sendById(Long id) {
        return sendById(id, CANAL_DEFECTO);
    }

    @Transactional
    public boolean sendById(Long id, String channel) {
        Optional<Notification> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return false;
        }
        Notification n = opt.get();
        return sendNotification(n, channel);
    }

    private boolean sendNotification(Notification notification, String channel) {
        NotificationStrategy strategy = strategyFactory.getStrategy(channel)
                .orElseThrow(() -> new IllegalArgumentException("Canal no disponible: " + channel));

        try {
            strategy.send(notification);
            notification.setEstado(EstadoNotificacion.ENVIADA);
            notification.setEnviadoAt(LocalDateTime.now());
            notification.setIntentosEnvio(notification.getIntentosEnvio() == null ? 1 : notification.getIntentosEnvio() + 1);
            repository.save(notification);
            logger.debug("Notificación enviada exitosamente: {}", notification.getId());
            return true;
        } catch (Exception ex) {
            int intentosActuales = notification.getIntentosEnvio() == null ? 0 : notification.getIntentosEnvio();
            notification.setIntentosEnvio(intentosActuales + 1);
            notification.setEstado(notification.getIntentosEnvio() >= 3 ? EstadoNotificacion.FALLIDA : EstadoNotificacion.REINTENTANDO);
            repository.save(notification);
            logger.warn("Falló el envío de la notificación {} por {}", notification.getId(), channel, ex);
            return false;
        }
    }
}


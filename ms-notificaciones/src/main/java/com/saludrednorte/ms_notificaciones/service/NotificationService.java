package com.saludrednorte.ms_notificaciones.service;

import com.saludrednorte.ms_notificaciones.entity.EstadoNotificacion;
import com.saludrednorte.ms_notificaciones.entity.Notification;
import com.saludrednorte.ms_notificaciones.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private static final String CANAL_DEFECTO = "EMAIL";
    private static final List<String> CANALES_PERMITIDOS = List.of("EMAIL", "SMS", "PUSH");

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public Notification create(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("La notificación no puede ser nula");
        }
        if (repository.existsByPacienteIdAndTipoAndMensajeAndEstado(
                notification.getPacienteId(),
                notification.getTipo(),
                notification.getMensaje(),
                EstadoNotificacion.PENDIENTE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una notificación pendiente equivalente");
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

    public List<Notification> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void sendPending() {
        findPending().forEach(notification -> markAsSent(notification, CANAL_DEFECTO));
    }

    @Transactional
    public boolean sendById(Long id) {
        return sendById(id, CANAL_DEFECTO);
    }

    @Transactional
    public boolean sendById(Long id, String channel) {
        String canalNormalizado = normalizeChannel(channel);
        Optional<Notification> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return false;
        }

        markAsSent(opt.get(), canalNormalizado);
        return true;
    }

    public List<String> getAvailableChannels() {
        return CANALES_PERMITIDOS;
    }

    private String normalizeChannel(String channel) {
        String canal = channel == null ? CANAL_DEFECTO : channel.trim().toUpperCase();
        if (!CANALES_PERMITIDOS.contains(canal)) {
            throw new IllegalArgumentException("Canal no disponible: " + channel);
        }
        return canal;
    }

    private void markAsSent(Notification notification, String channel) {
        notification.setEstado(EstadoNotificacion.ENVIADA);
        notification.setEnviadoAt(LocalDateTime.now());
        notification.setIntentosEnvio(notification.getIntentosEnvio() == null ? 1 : notification.getIntentosEnvio() + 1);
        repository.save(notification);
        logger.info("Notificación {} enviada por {}", notification.getId(), channel);
    }
}

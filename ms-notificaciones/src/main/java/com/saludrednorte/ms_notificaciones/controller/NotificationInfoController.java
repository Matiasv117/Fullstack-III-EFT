package com.saludrednorte.ms_notificaciones.controller;

import com.saludrednorte.ms_notificaciones.dto.ChannelInfoDTO;
import com.saludrednorte.ms_notificaciones.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para información del sistema de notificaciones
 * Expone endpoints de información y configuración
 */
@RestController
@RequestMapping("/api/notifications/info")
public class NotificationInfoController {

    private final NotificationService notificationService;

    public NotificationInfoController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * GET /api/notifications/info/channels
     * Obtiene la lista de canales de notificación disponibles
     * @return Lista de canales disponibles
     */
    @GetMapping("/channels")
    public ResponseEntity<List<ChannelInfoDTO>> getAvailableChannels() {
        List<ChannelInfoDTO> channels = notificationService.getAvailableChannels().stream()
                .map(channel -> new ChannelInfoDTO(
                        channel,
                        "Canal de notificación: " + channel
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(channels);
    }

    /**
     * GET /api/notifications/info/health
     * Verifica la salud del servicio de notificaciones
     * @return Estado del servicio
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Microservicio de Notificaciones operacional");
    }
}

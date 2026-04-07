package com.saludrednorte.ms_notificaciones.dto;

import com.saludrednorte.ms_notificaciones.entity.Notification;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Notification y sus DTOs
 * Implementa el patrón de conversión de datos entre capas
 */
@Component
public class NotificationMapper {

    /**
     * Convierte un RequestDTO a una entidad Notification
     */
    public Notification requestDtoToEntity(NotificationRequestDTO dto) {
        Notification notification = new Notification();
        notification.setPacienteId(dto.getPacienteId());
        notification.setTipo(dto.getTipo());
        notification.setMensaje(dto.getMensaje());
        return notification;
    }

    /**
     * Convierte una entidad Notification a un ResponseDTO
     */
    public NotificationResponseDTO entityToResponseDto(Notification notification) {
        return new NotificationResponseDTO(
            notification.getId(),
            notification.getPacienteId(),
            notification.getTipo(),
            notification.getMensaje(),
            notification.getEstado(),
            notification.getCreadoAt(),
            notification.getEnviadoAt(),
            notification.getIntentosEnvio()
        );
    }
}

